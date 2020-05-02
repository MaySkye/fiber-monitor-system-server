package com.rcd.fiber.base.soap.wsn;

import com.rcd.fiber.base.soap.INotificationProcess;

import javax.jws.WebService;

/**
 * 发送方处理程序，在发布注册后得到返回的 publishAddress
 */
@WebService(endpointInterface= "com.rcd.fiber.base.soap.INotificationProcess",
        serviceName="NotificationProcessImpl")
public class SendNotificationProcessImpl implements INotificationProcess {
    @Override
    public String notificationProcess(String notification) {
//        Trans.publishAddress = splitString(notification, "<message>", "</message>");
//        System.out.println("注册成功，返回发布地址：" + Trans.publishAddress);
        return notification;
    }

    public String splitString(String string, String start, String end)
    {
        int from = string.indexOf(start) + start.length();
        int to = string.indexOf(end);
        return string.substring(from, to);
    }
}
