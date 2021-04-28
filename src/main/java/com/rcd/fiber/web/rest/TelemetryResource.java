package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.service.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public JSONObject getMonitorInfos(@RequestBody JSONObject params) {
        return service.getMonitorInfos(params);
    }


    @PostMapping("/validSSO")
    @ResponseBody
    public JSONObject validSSO(@RequestBody JSONObject info, HttpServletRequest request) {
        System.out.println("start");
        try {
            // 尝试解析Token
            String token_base64 = request.getHeader("Authorization").substring(7);
            String user = TokenProvider.getClaims(token_base64).getSubject();
            UserJWTController.userMd5Map.put(user, info.getString("hash"));
            JSONObject res = new JSONObject();
            res.put("type", "success");
            return res;
        } catch (Exception e) {
            JSONObject res = new JSONObject();
            res.put("type", "error");
            return res;
        }
    }
}
