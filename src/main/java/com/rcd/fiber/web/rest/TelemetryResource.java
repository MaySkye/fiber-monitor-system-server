package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.repository.InfluxRepository;
import com.rcd.fiber.service.TelemetryService;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:
 * @Date: 2019/6/17 14:58
 */
@RestController
@RequestMapping("/telemetry")
@CrossOrigin(origins = "*") // 跨域
public class TelemetryResource {
    private final Logger logger = LoggerFactory.getLogger(TelemetryResource.class);
    @Autowired
    private TelemetryService service;



    @PostMapping("/getMonitorValueBySite")
    @ResponseBody
    public JSONObject getVoltDBMonitorValue(@RequestBody JSONObject params) {
        String siteName = params.getString("siteName");
        // 获取运行参数
        List<TelemetryDTO> vals = service.getVoltDBMonitorValue(siteName);
        List<SignalDTO> sigs = service.getVoltDBSignalValue(siteName);
        JSONArray runtimeInfos = new JSONArray();
        runtimeInfos.addAll(vals);
        runtimeInfos.addAll(sigs);
        // 获取告警信息
        JSONArray eventInfos = new JSONArray();
        UserNotificationProcessImpl.eventInfoDTOMap.get(siteName);
        // 返回结果
        JSONObject res = new JSONObject();
        res.put("runtimeInfos", runtimeInfos);
        res.put("eventInfos", eventInfos);
        return res;
    }

    @PostMapping("/getMonitorInfos")
    @ResponseBody
    public JSONObject getMonitorInfos(@RequestBody JSONObject params) {
        return service.getMonitorInfos(params);
    }
}
