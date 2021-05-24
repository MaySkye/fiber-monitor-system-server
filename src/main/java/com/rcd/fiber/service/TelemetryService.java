package com.rcd.fiber.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.config.HardwareControlMySQLConfig;
import com.rcd.fiber.config.VoltdbJdbcBaseConfig;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.repository.InfluxRepository;
import com.rcd.fiber.service.dto.EventInfoDTO;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO;
import com.rcd.fiber.utils.BeanNameUtil;
import com.rcd.fiber.utils.SQLResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiConsumer;

/**
 * @Author:
 * @Date: 2019/6/17 14:52
 */
@Service
@Transactional
public class TelemetryService {
    private final Logger log = LoggerFactory.getLogger(TelemetryService.class);
    @Resource(type = InfluxRepository.class)
    private InfluxRepository influxRepository;
    @Resource
    private HardwareControlMySQLConfig hardwareControlMySQLConfig;

    /**
     * 获取监控信息
     *
     * @param params
     * @return
     */
    public JSONObject getMonitorInfos(@RequestBody JSONArray params) {
        // 返回结果
        JSONObject res = new JSONObject();
        // 设备运行参数
        JSONArray runtimeInfos = new JSONArray();
        // 告警信息
        JSONArray eventInfos = new JSONArray();
        // 遍历所有站点
        for(int i = 0; i < params.size(); i++){
            JSONObject param = params.getJSONObject(i);
            // 获取查询参数
            String siteName = param.getString("siteName");
            JSONArray deviceNames = param.getJSONArray("deviceNames");
            // 根据站点、设备名查influxDB
            for(int j = 0; j < deviceNames.size(); j++){
                JSONObject query = new JSONObject();
                query.put("site_name", siteName);
                query.put("device_name", deviceNames.getString(j));
                // Consumer
                BiConsumer<String, Object> consumer = (String deviceName, Object infosObj) -> {
                    JSONArray infos = (JSONArray) infosObj;
                    for (int k = 0; k < infos.size(); k++) {
                        JSONObject info = infos.getJSONObject(k);
                        // 添加分组查询被省去的字段
                        info.put("data_name", deviceName);
                        runtimeInfos.add(info);
                    }
                };
                // 查询signal表
                JSONObject sigs = influxRepository.getSignalInfo(query);
                if (sigs != null) sigs.forEach(consumer);
                // 查询telemetry表
                JSONObject tels = influxRepository.getTelemetryInfo(query);
                if (tels != null) tels.forEach(consumer);
            }
            // 获取告警信息
            List<EventInfoDTO> events = UserNotificationProcessImpl.notificationHandler.getEventsBySite(siteName);
            eventInfos.addAll(events);
        }

        // 获取查询参数
        res.put("runtimeInfos", runtimeInfos);
        res.put("eventInfos", eventInfos);
        return res;
    }

    /**
     * 获取可遥控的参数
     *
     * @param params
     * @return
     * @throws SQLException
     */
    public List<JSONObject> getAllControlRecordByParams(JSONObject params) throws Exception {
        String sql = "select * from control_info where site_name = ?";
        Connection connection = hardwareControlMySQLConfig.getConnection();
        PreparedStatement prepSql = connection.prepareStatement(sql);
        prepSql.setString(1, params.getString("siteName"));
        ResultSet resultSet = prepSql.executeQuery();
        List<JSONObject> resultList = SQLResultSetHandler.getResultList(resultSet);
        resultSet.close();
        prepSql.close();
        connection.close();
        return resultList;
    }
}
