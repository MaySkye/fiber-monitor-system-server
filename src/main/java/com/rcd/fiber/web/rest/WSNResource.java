package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.service.AlarmDeviceService;
import com.rcd.fiber.service.ControlService;
import com.rcd.fiber.service.TelemetryService;
import com.rcd.fiber.service.WSNService;
import com.rcd.fiber.service.dto.BlockQueue;
import com.rcd.fiber.service.dto.EventInfoDTO;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/wsn")
@CrossOrigin // 防止跨域
public class WSNResource {

    private final WSNService wsnService;
    private final AlarmDeviceService alarmDeviceService;

    //public static List<EventInfoDTO> eventInfoDTOList =new LinkedList<>();
    public static Map<String,List<EventInfoDTO>> eventInfoDTOMap = new HashMap<>();
    public static Lock lock = new ReentrantLock();
    public static boolean isStartSub = false;
    //public static BlockQueue<EventInfoDTO> blockQueue = new BlockQueue<>(new LinkedList<>(),new CopyOnWriteArrayList<>(),30);

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
    @PostMapping("/getEventInfo")
    @Timed
    @ResponseBody
    public String getEventInfo(@RequestBody String info)
    {
        JSONObject obj = JSONObject.parseObject(info);
        String siteName = obj.getString("site_name");
       // lock.lock();
        String jsonEventInfoDTOList = JSON.toJSONString(eventInfoDTOMap.get(siteName));
        System.out.println("jsonEventInfoDTOList: "+jsonEventInfoDTOList);
        //lock.unlock();
        return jsonEventInfoDTOList;
    }

    /*zhaoyi*/
    @PostMapping("/startSub")
    @Timed
    @ResponseBody
    public void startSub(@RequestBody String info)
    {

        if(!isStartSub){
            JSONObject obj = JSONObject.parseObject(info);
            String siteName = obj.getString("site_name");
            String id = obj.getString("id");
            String topic = obj.getString("topic");
            //status=1代表开启了wsn
            if (status.equals("1")) {
                wsnService.startSubService(id,topic);
                isStartSub = true;
            }
        }
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
