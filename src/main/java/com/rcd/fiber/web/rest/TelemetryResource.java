package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.service.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
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

    @PostMapping("/getMonitorInfos")
    @ResponseBody
    public JSONObject getMonitorInfos(@RequestBody JSONArray params) {
        return service.getMonitorInfos(params);
    }


    @PostMapping("/getAllControlRecordByParams")
    @ResponseBody
    public List<JSONObject> getAllControlRecordByParams(@RequestBody JSONObject params) throws Exception {
        return service.getAllControlRecordByParams(params);
    }
}
