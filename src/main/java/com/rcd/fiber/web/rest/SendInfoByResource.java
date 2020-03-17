package com.rcd.fiber.web.rest;

import com.rcd.fiber.service.AlarmDeviceService;
import com.rcd.fiber.service.ControlService;
import com.rcd.fiber.service.TelemetryService;
import com.rcd.fiber.service.util.WSService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HUHU
 * @Date: 2019/6/28 16:47
 */

@RestController
@RequestMapping("/wsn")
@CrossOrigin // 防止跨域
public class SendInfoByResource {

    private final WSService wsService;
    private final AlarmDeviceService alarmDeviceService;
    private final ControlService controlService;
    private final TelemetryService telemetryService;
    Logger logger = LoggerFactory.getLogger(SendInfoByResource.class);

    @Value("${wsn.send.status}")
    private String status;

    public SendInfoByResource(WSService wsService, AlarmDeviceService alarmDeviceService, ControlService controlService, TelemetryService telemetryService) {
        this.wsService = wsService;
        this.alarmDeviceService = alarmDeviceService;
        this.controlService = controlService;
        this.telemetryService = telemetryService;
    }

    @GetMapping("/sendClosed")
    @Timed
    public ResponseEntity<Map<String, String>> sendInfo() {
        //status=1代表开启了wsn
        if (status.equals("1")) {
            // 异步执行关闭设备
            wsService.sendInfoByWSN("xian","拍频比对设备A1.29","光频_频率稳定度A1.29");
        }
        // 同时操作数据
        int a = alarmDeviceService.updataData();
        logger.info("更新数据："+ a);

        Map<String, String> map = new HashMap<>();
        map.put("status", "1");
        map.put("msg", "关闭成功");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/sendCloseCmdById/{infoStr}")
    @Timed
    public String sendCloseCmdById(@PathVariable(value = "infoStr") String infoStr)
    {
        System.out.println("------------"+"status:"+status);
        String[] strings=infoStr.split(":");
        String site_name=strings[0];
        String device_name=strings[1];
        String data_name=strings[2];
        System.out.println("------------"+"site_name:"+site_name);
        System.out.println("------------"+"device_name:"+device_name);
        System.out.println("------------"+"data_name:"+data_name);

        //wsService.sendInfoByWSN(site_name,device_name,data_name);
        //status=1代表开启了wsn
        if (status.equals("1")) {
            // 异步执行关闭设备
            wsService.sendInfoByWSN("xian","拍频比对设备A1.29","光频_频率稳定度A1.29");

        }
        //修改control数据表的value,将0改为1
        controlService.updateValue(1,site_name,device_name,data_name);

        //直接返回true,还无法得到wsService.sendInfoByWSN的返回结果
        return "true";
    }
}
