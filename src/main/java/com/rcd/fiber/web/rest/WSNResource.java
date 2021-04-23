package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.base.start.PublisherHandler;
import com.rcd.fiber.service.WSNService;
import io.micrometer.core.annotation.Timed;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wsn")
@CrossOrigin("*")
public class WSNResource {
    @Autowired
    private WSNService wsnService;

    /**
     * 发送遥控信息：修改设备监控值
     *
     * @param info xml格式的遥控信息
     * @return
     */
    @PostMapping("/sendControlInfo")
    @Timed
    @ResponseBody
    public JSONObject sendControlInfo(@RequestBody String info) {
        System.out.println(" editTelemetryValue-info: " + info);
        wsnService.sendControlInfo(info);
        return new JSONObject();
    }
}
