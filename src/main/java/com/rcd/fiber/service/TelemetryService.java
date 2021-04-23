package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.config.VoltdbJdbcBaseConfig;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.repository.InfluxRepository;
import com.rcd.fiber.service.dto.EventInfoDTO;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    //    @Autowired
    private VoltdbJdbcBaseConfig voltdbJdbcBaseConfig;

    //王伟（2020-1-11）： 根据站点名称，查询voltDB内的遥测数据
    public List<TelemetryDTO> getVoltDBMonitorValue(String siteName) {
        ResultSet set;
        try {
            set = voltdbJdbcBaseConfig.executeQuery("select * from TELEMETRY where site_name = '" + siteName);
            List<TelemetryDTO> list = voltdbJdbcBaseConfig.populate(set, TelemetryDTO.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseConfig.closeConnection();
        }
        return null;
    }

    //王伟（2020-1-11）： 根据站点名称，查询voltDB内的遥控数据
    public List<SignalDTO> getVoltDBSignalValue(String siteName) {
        ResultSet set;
        try {
            set = voltdbJdbcBaseConfig.executeQuery("select * from telesignalling where site_name = '" + siteName);
            List<SignalDTO> list = voltdbJdbcBaseConfig.populate(set, SignalDTO.class);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseConfig.closeConnection();
        }
        return null;
    }


    /**
     * 获取监控信息
     *
     * @param params
     * @return
     */
    public JSONObject getMonitorInfos(@RequestBody JSONObject params) {
        // 获取查询参数
        String siteName = params.getString("siteName");
        JSONArray deviceNames = params.getJSONArray("deviceNames");

        // 设备运行参数
        JSONArray runtimeInfos = new JSONArray();

        for (int i = 0; i < deviceNames.size(); i++) {
            JSONObject query = new JSONObject();
            query.put("site_name", siteName);
            query.put("device_name", deviceNames.getString(i));
            // Consumer
            BiConsumer<String, Object> consumer = (String deviceName, Object infosObj) -> {
                JSONArray infos = (JSONArray) infosObj;
                for (int j = 0; j < infos.size(); j++) {
                    JSONObject info = infos.getJSONObject(j);
                    // 添加分组查询被省去的字段
                    info.put("data_name", deviceName);
                    runtimeInfos.add(info);
                }
            };
            JSONObject groupRes = null;
            // 查询signal表
            groupRes = influxRepository.getSignalInfo(query);
            if (groupRes != null) groupRes.forEach(consumer);
            // 查询telemetry表
            groupRes = influxRepository.getTelemetryInfo(query);
            if (groupRes != null) groupRes.forEach(consumer);
        }

        // 返回结果
        JSONObject res = new JSONObject();
        // 获取告警信息
        res.put("runtimeInfos", runtimeInfos);
        res.put("eventInfos", UserNotificationProcessImpl.notificationHandler.getEventsBySite(siteName));
        return res;
    }
}
