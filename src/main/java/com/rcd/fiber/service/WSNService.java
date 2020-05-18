package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.pub.CasePublish;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.base.start.RegesterAddr;

import com.rcd.fiber.base.sub.CaseSubscirbe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Endpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 发布与订阅主题
 */
@Service
@Transactional
public class WSNService {
    private final Logger logger = LoggerFactory.getLogger(WSNService.class);
    private String addr;

    //对应application-dev.yml配置文件
    @Value("${wsn.receive.receiveAddr1}")
    private String receiveAddr1;
    @Value("${wsn.receive.receiveAddr2}")
    private String receiveAddr2;
    // 发布的时候
    @Value("${wsn.send.sendAddr3}")
    private String sendAddr3;

    // 获取地址
    private String getAddr(){
        // 将地址的声明为全局变量自动获取
        if (this.addr == null){
            this.addr = RegesterAddr.getAddr();
        }
        return addr;
    }

    // 获取订阅消息
    public void   startSubService(String id,String topic){
        //本机地址
        String wsnAddr = receiveAddr1;
        String receiveAddr = receiveAddr2;
        System.out.println("wsnAddr: "+wsnAddr);
        System.out.println("receiveAddr: "+receiveAddr);
        //CaseSubscirbe param 1:订阅地址 param 2：wsn地址 param 3:订阅主题名
        CaseSubscirbe sub = new CaseSubscirbe(receiveAddr,wsnAddr,topic);
        //订阅主题
        sub.subscibe();
    }

    // 发布主题，关闭设备
    @Async
    public void sendInfoByWSN(String info){
        /*Trans trans = RegesterAddr.getTrans();
        int result = trans.sendMethod(info, getAddr(), sendParm1, "admin","control");
        System.out.println(sendParm1);
        System.out.println("result: "+result);
        return result;*/
        System.out.println(receiveAddr1);
        System.out.println(sendAddr3);
        CasePublish pub = new CasePublish(receiveAddr1,sendAddr3,"control");
        //发布主题
        pub.register();
        //发布消息void
        pub.publishmsg(info);
    }

    // 王伟：修改设备监控值
    @Async
    public JSONObject editTelemetryValue(String info)
    {

        System.out.println(receiveAddr1);
        System.out.println(sendAddr3);
        CasePublish pub = new CasePublish(receiveAddr1,sendAddr3,"control");
        //发布主题
        pub.register();
        //发布消息
        pub.publishmsg(info);
        JSONObject res = new JSONObject();
        res.put("type","success");
        res.put("msg","Test Method: editTelemetryValue");
        return res;
    }
}
