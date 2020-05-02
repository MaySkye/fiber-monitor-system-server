package com.rcd.fiber.base.soap.wsn;

import com.rcd.fiber.base.soap.INotificationProcess;

import javax.jws.WebService;

/**
 * 客户端ws处理程序
 * 对应的是wsn层ws处理程序
 */
@WebService(endpointInterface= "com.rcd.fiber.base.soap.INotificationProcess",
        serviceName="NotificationProcessImpl")
public class UserNotificationProcessImpl implements INotificationProcess {
    @Override
    public String  notificationProcess(String notification) {
        String topic = splitString(notification, "<topic>", "</topic>");
        String msg = splitString(notification, "<message>", "</message>");
        System.out.println("收到订阅主题 " + topic + " 文本消息：" + msg);
        return topic;
    }

    public String splitString(String string, String start, String end)
    {
        int from = string.indexOf(start) + start.length();
        int to = string.indexOf(end);
        return string.substring(from, to);
    }
}
