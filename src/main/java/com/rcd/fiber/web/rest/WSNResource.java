package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.service.AlarmDeviceService;
import com.rcd.fiber.service.ControlService;
import com.rcd.fiber.service.TelemetryService;
import com.rcd.fiber.service.WSNService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wsn")
@CrossOrigin // 防止跨域
public class WSNResource {

    private final WSNService wsnService;
    private final AlarmDeviceService alarmDeviceService;

    Logger logger = LoggerFactory.getLogger(WSNResource.class);

    @Value("${wsn.send.status}")
    private String status;

    public WSNResource(WSNService wsnService, AlarmDeviceService alarmDeviceService) {
        this.wsnService = wsnService;
        this.alarmDeviceService = alarmDeviceService;
    }

    @GetMapping("/sendClosed")
    @Timed
    public ResponseEntity<Map<String, String>> sendInfo() {
        //status=1代表开启了wsn
        if (status.equals("1")) {
            // 异步执行关闭设备
            wsnService.sendInfoByWSN("xian");
        }
        // 同时操作数据
        int a = alarmDeviceService.updataData();
        logger.info("更新数据："+ a);

        Map<String, String> map = new HashMap<>();
        map.put("status", "1");
        map.put("msg", "关闭成功");
        return ResponseEntity.ok(map);
    }

    /*zhaoyi*/
    @RequestMapping(value = "/sendControlInfo", method = RequestMethod.POST)
    @Timed
    @ResponseBody
    public void sendControlInfo(@RequestBody String info)
    {
        System.out.println("sendControlInfo-info:"+info);
        int result;
        //status=1代表开启了wsn
        if (status.equals("1")) {
            // 异步执行关闭设备
            wsnService.sendInfoByWSN(info);
        }
        //未开启wsn
        else{
            return;
        }

    }

    /*zhaoyi*/
    @GetMapping("/getEventInfo")
    @Timed
    @ResponseBody
    public String getEventInfo(@RequestBody String info)
    {
        info=info.substring(1,info.length()-1);
        System.out.println("info:"+info);
        JSONObject obj = JSONObject.parseObject(info);
        String siteName = obj.getString("site_name");
        String id = obj.getString("id");
        String topic = obj.getString("topic");
        System.out.println("getEventInfo-siteName: "+siteName+" id: "+id+" topic: "+topic);
        System.out.println("getEventInfo-status: "+status);

        String resInfo; //返回设备名称+属性名称+值
        //status=1代表开启了wsn
        if (status.equals("1")) {
            resInfo = wsnService.getInfoByWSN(id,topic);
        }else{
            resInfo = null;
        }
        System.out.println("getEventInfo-resInfo: "+resInfo);
        return resInfo;
    }

    // 王伟，修改设备监控值
    @PostMapping("/editTelemetryValue")
    @Timed
    @ResponseBody
    public JSONObject editTelemetryValue(@RequestBody String info)
    {
        System.out.println(" editTelemetryValue-info: "+info);
        return wsnService.editTelemetryValue(info);
    }
}
