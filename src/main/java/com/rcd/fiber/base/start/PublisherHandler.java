package com.rcd.fiber.base.start;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.pub.CasePublish;
import com.rcd.fiber.utils.WWLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;


@Component
public class PublisherHandler {
    // 对应application-dev.yml配置文件
    // 发布订阅系统开启与否
    @Value("${wsn.receive.status}")
    private String status;
    // 接收地址
    @Value("${wsn.receive.receiveAddr1}")
    private String receiveAddr1;
    @Value("${wsn.receive.receiveAddr2}")
    private String receiveAddr2;
    // 发送地址
    @Value("${wsn.send.sendAddr3}")
    private String sendAddr3;

    @PostConstruct
    public void startPubService() {
        WWLogger.info("发布系统状态：", status, MessageFormat.format("recAdd1：{0}，recAdd2：{1}， sendAddr3：{2}", receiveAddr1, receiveAddr2, sendAddr3));
    }

    // 王伟：修改设备监控值
    @Async
    public JSONObject sendControlInfo(String info) {
        CasePublish pub = new CasePublish(receiveAddr1, sendAddr3, "control");
        //发布主题
        pub.register();
        //发布消息
        pub.publishmsg(info);
        JSONObject res = new JSONObject();
        res.put("type", "success");
        res.put("msg", "Test Method: editTelemetryValue");
        return res;
    }
}
