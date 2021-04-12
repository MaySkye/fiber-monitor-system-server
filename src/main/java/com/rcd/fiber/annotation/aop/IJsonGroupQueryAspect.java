package com.rcd.fiber.annotation.aop;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.annotation.IJsonGroupQuery;
import com.rcd.fiber.utils.SqlStringProcessor;
import com.rcd.fiber.utils.WWLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class IJsonGroupQueryAspect {

    private static InfluxDB influxDB;

    @Value("${spring.influx.database}")
    private String database;

    @Autowired
    public void setInfluxDB(InfluxDB influxDB)
    {
        IJsonGroupQueryAspect.influxDB = influxDB;
    }


    @Pointcut(value = "@annotation(com.rcd.fiber.annotation.IJsonGroupQuery)")
    private void pointcut() {
    }

    // 根据目标方法返回值是否为JSONArray，来决定返回值是JSONObject或JSONArray
    @Around(value = "pointcut() && @annotation(iquery)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, IJsonGroupQuery iquery) throws Exception {
        // 预处理sql
        String sql = iquery.value();
        Object[] args = proceedingJoinPoint.getArgs();
        /* 如果有查询参数，则预处理 */
        if (args.length > 0) {
            if (args[0].getClass().getTypeName().equals(JSONObject.class.getTypeName())) {
                WWLogger.debug("有查询参数");
                JSONObject arg = (JSONObject) args[0];
                sql = SqlStringProcessor.processSQL(iquery.value(), arg);
            } else {
                throw new Exception("传入参数必须为JSONObejct类型");
            }
        }
        /* 获取返回值类型 */
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String returnType = method.getGenericReturnType().getTypeName();
        WWLogger.debug("目标函数返回值类型为：" + returnType);
        // 查询
        Query query = new Query(sql, database);
        QueryResult queryResult = influxDB.query(query, TimeUnit.MILLISECONDS);
        // 提取结果
        List<QueryResult.Result> resultList = queryResult.getResults();
        QueryResult.Result result = resultList.get(0);
        WWLogger.debug("返回结果是否有数据：" + String.valueOf( result.getSeries() != null));
        // 空集判断
        if (result.getSeries() == null) {
            if (returnType.equals(JSONObject.class.getTypeName()))
                return null;
            else if (returnType.equals(JSONArray.class.getTypeName()))
                throw new Exception("Group By操作错误！", new Throwable("返回值必须为JSONObject！"));
        }
        // 创建分组结果，以tags字段命名
        JSONObject returnData = new JSONObject();
        List<QueryResult.Series> allSeries = result.getSeries();
        for (QueryResult.Series series : allSeries) {
            // 分析整合，由于Influxdb返回顺序字段名、顺序值，所以要手动加工成标准json格式
            List<String> columnNames = series.getColumns();
            List<List<Object>> points = series.getValues();
            JSONArray itemReturnData = new JSONArray();
            for (List point : points) {
                JSONObject json = new JSONObject();
                for (int i = 0; i < columnNames.size(); i++) {
                    json.put(columnNames.get(i), point.get(i));
                }
                itemReturnData.add(json);
            }
            Map<String, String> tags = series.getTags();
            ArrayList<String> tagList = new ArrayList<String>(tags.keySet());
            String tagName = tags.get(tagList.get(0));
            returnData.put(tagName, itemReturnData);
        }
        if (returnType.equals(JSONObject.class.getTypeName())) {
            return returnData;
        } else {
            throw new Exception("返回值必须为JSONObject类型！");
        }
    }

    @AfterThrowing(value = "pointcut() && @annotation(iquery)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, IJsonGroupQuery iquery, Exception ex) {
        WWLogger.debug("【异常】【IJsonGroupQuery.afterThrowing切面方法】请求：" + iquery.value());
        ex.printStackTrace();
    }
}

