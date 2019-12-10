package com.rcd.fiber.web.rest;

import com.rcd.fiber.domain.entity.AlarmDevice;
import com.rcd.fiber.service.AlarmDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HUHU
 * @Date: 2019/7/21 12:55
 */
@RestController
@RequestMapping("/alarmDevice")
@CrossOrigin // 防止跨域
public class AlarmDeviceResource {
    Logger logger = LoggerFactory.getLogger(AlarmDeviceResource.class);

    private final AlarmDeviceService service;

    public AlarmDeviceResource(AlarmDeviceService service) {
        this.service = service;
    }

    @GetMapping(value = "getAlarmDevice")
    public ResponseEntity<Map<String, Serializable>> getAlarmDevice(){
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        List<AlarmDevice> list = service.getAlarmDevice(0);

        if (list.isEmpty())
        {
            map.put("status", 0);
            map.put("msg", "无预警信息");
            return  ResponseEntity.ok(map);
        }
        map.put("status", 1);
        map.put("msg", "有预警信息");
        // map.put("imgurl", url);
        return ResponseEntity.ok(map);
    }

}
