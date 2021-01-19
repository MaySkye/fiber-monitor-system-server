package com.rcd.fiber.annotation.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.annotation.VoltQuery;
import com.rcd.fiber.base.VoltdbJdbcBaseDao;
import com.rcd.fiber.service.dto.TelemetryDTO;
import com.rcd.fiber.utils.SqlStringProcessor;
import com.rcd.fiber.utils.WWLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;

@Component
@Aspect
public class VoltQueryAop {
    // 声明切入点
    @Pointcut("@annotation(com.rcd.fiber.annotation.VoltQuery)")
    private void pointcut() {
    }

    @Autowired
    private VoltdbJdbcBaseDao voltDao;

    @Around("pointcut() && @annotation(query)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, VoltQuery query) throws Exception {
        // 获取SQL语句
        String sql = query.value();
        Object[] args = proceedingJoinPoint.getArgs();
        /* 如果有查询参数，则预处理 */
        if (args.length > 0) {
            args[0] = JSONObject.toJSON(args[0]);
            if (args[0].getClass().getTypeName().equals(JSONObject.class.getTypeName())) {
                WWLogger.debug("有查询参数");
                JSONObject arg = (JSONObject) args[0];
                sql = SqlStringProcessor.processSQL(sql, arg);
            } else {
                throw new Exception("传入参数必须为JSONObejct类型");
            }
        }
        /* 获取返回值类型 */
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String returnType = method.getGenericReturnType().getTypeName();
        WWLogger.debug("目标函数返回值类型为：" + returnType);
        ResultSet resultSet = voltDao.executeQuery(sql);
        List resultList = voltDao.populate(resultSet, method.getGenericReturnType().getClass());
        return resultList;
    }
}
