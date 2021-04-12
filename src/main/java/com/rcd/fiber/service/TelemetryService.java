package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.VoltdbJdbcBaseDao;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.repository.InfluxRepository;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    //王伟（2020-1-11）： 根据站点名称，查询voltDB内的遥测数据
    public List<TelemetryDTO> getVoltDBMonitorValue(String siteName) {
        VoltdbJdbcBaseDao voltdbJdbcBaseDao = new VoltdbJdbcBaseDao();
        ResultSet set;
        try {
            set = voltdbJdbcBaseDao.executeQuery("select * from TELEMETRY where site_name = '" + siteName);
            List<TelemetryDTO> list = voltdbJdbcBaseDao.populate(set, TelemetryDTO.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseDao.closeConnection();
        }
        return null;
    }

    //王伟（2020-1-11）： 根据站点名称，查询voltDB内的遥控数据
    public List<SignalDTO> getVoltDBSignalValue(String siteName) {
        VoltdbJdbcBaseDao voltdbJdbcBaseDao = new VoltdbJdbcBaseDao();
        ResultSet set;
        try {
            set = voltdbJdbcBaseDao.executeQuery("select * from telesignalling where site_name = '" + siteName);
            List<SignalDTO> list = voltdbJdbcBaseDao.populate(set, SignalDTO.class);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseDao.closeConnection();
        }
        return null;
    }


    /**
     * 获取监控信息
     * @param params
     * @return
     */
    public JSONObject getMonitorInfos(@RequestBody JSONObject params) {
        // 获取查询参数
        String siteName = params.getString("siteName");
        JSONArray items = params.getJSONArray("items");

        // 设备运行参数
        JSONArray runtimeInfos = new JSONArray();

        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONArray dataNames = item.getJSONArray("dataNames");
            for(int j =0; j < dataNames.size(); j++){
                // 新建查询参数
                JSONObject query = new JSONObject();
                query.put("site_name", siteName);
                query.put("device_name", item.getString("deviceId"));
                String dataName = dataNames.getString(j);
                query.put("data_name", dataName);

                // 查询
                JSONObject info = null;
                if (dataName.indexOf("锁定状态") != -1 || dataName.indexOf("运行状态") != -1) {
                    info = influxRepository.getSignalInfo(query);
                } else {
                    info = influxRepository.getTelemetryInfo(query);
                }
                if (info != null) {
                    runtimeInfos.add(info);
                }
            }
        }

        // 获取告警信息
        JSONArray eventInfos = new JSONArray();
        UserNotificationProcessImpl.eventInfoDTOMap.get(siteName);

        // 返回结果
        JSONObject res = new JSONObject();
        res.put("runtimeInfos", runtimeInfos);
        res.put("eventInfos", eventInfos);
        return res;
    }
}
