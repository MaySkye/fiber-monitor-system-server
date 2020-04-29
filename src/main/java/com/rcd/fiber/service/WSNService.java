package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.start.RegesterAddr;
import com.rcd.fiber.base.wsn.SendWSNCommand;
import com.rcd.fiber.base.wsn.Trans;
import com.rcd.fiber.base.wsn.UserNotificationProcessImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Endpoint;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Value("${wsn.receive.wsnAddr1}")
    private String receiveParm1;
    @Value("${wsn.receive.receiveAddr2}")
    private String receiveParm2;
    // 发布的时候
    @Value("${wsn.send.sendAddr3}")
    private String sendParm1;

    // 获取地址
    private String getAddr(){
        // 将地址的声明为全局变量自动获取
        if (this.addr == null){
            this.addr = RegesterAddr.getAddr();
        }
        return addr;
    }

    // 获取订阅消息
    @Async
    public String  getInfoByWSN(String id,String topic){
        //本机地址
        String wsnAddr = receiveParm1;
        String receiveAddr = receiveParm2;
        System.out.println("wsnAddr: "+wsnAddr);
        System.out.println("receiveAddr: "+receiveAddr);
        SendWSNCommand receive = new SendWSNCommand(receiveAddr, wsnAddr);
        // 消息处理逻辑
        UserNotificationProcessImpl implementor = new UserNotificationProcessImpl();
        // 启接收服
        Endpoint endpint = Endpoint.publish(receiveAddr, implementor);
        String info = receive.subscribe(id, topic);
        System.out.println("订阅消息："+info);
        return info;
    }

    // 发布主题，关闭设备
    @Async
    public int sendInfoByWSN(String info){
        Trans trans = RegesterAddr.getTrans();
        int result = trans.sendMethod(info, getAddr(), sendParm1, "admin","test4");
        System.out.println(sendParm1);
        System.out.println("result: "+result);
        return result;
    }

    // 王伟：修改设备监控值
    @Async
    public JSONObject editTelemetryValue(String info)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = df.format(new Date());
        Trans trans = RegesterAddr.getTrans();
        trans.sendMethod(info, getAddr(), sendParm1, "admin","test4");
        System.out.println(sendParm1);
        JSONObject res = new JSONObject();
        res.put("type","success");
        res.put("msg","Test Method: editTelemetryValue");
        return res;
    }
}
