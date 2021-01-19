package com.rcd.fiber.base.start;

import com.rcd.fiber.base.sub.CaseSubscirbe;
import com.rcd.fiber.utils.WWLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

/**
 * 订阅系统启动器
 */

@Component
public class SubscriberHandler {
    static Logger logger = LoggerFactory.getLogger(SubscriberHandler.class);

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


    // 获取订阅消息
    @PostConstruct
    public void startSubService() {
        WWLogger.info("订阅系统状态：", status, MessageFormat.format("recAdd1：{0}，recAdd2：{1}， sendAddr3：{2}", receiveAddr1, receiveAddr2, sendAddr3));
        if (status.equals("1")) {
            // CaseSubscirbe param 1:订阅地址 param 2：wsn地址 param 3:订阅主题名
            CaseSubscirbe sub = new CaseSubscirbe(receiveAddr2, receiveAddr1, "event");
            //订阅主题
            sub.subscibe();
        }
    }
}
