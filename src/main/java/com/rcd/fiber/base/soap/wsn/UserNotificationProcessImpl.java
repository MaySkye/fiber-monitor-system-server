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
    private String info;

    public UserNotificationProcessImpl() {

    }

    @Override
    public String  notificationProcess(String notification) {
        String topic = splitString(notification, "<topic>", "</topic>");
        String msg = splitString(notification, "<message>", "</message>");

        //得到eventType siteName devName  dataName detectedValue
        String eventType = splitString(notification, "<eventType>", "</eventType>");
        String devName = splitString(notification, "<devName>", "</devName>");
        String dataName = splitString(notification, "<dataName>", "</dataName>");
        String detectedValue = splitString(notification, "<detectedValue>", "</detectedValue>");


        setInfo(msg);
        System.out.println("收到订阅主题 " + topic + " 文本消息：" + msg);
        return notification;
    }

    public String splitString(String string, String start, String end)
    {
        int from = string.indexOf(start) + start.length();
        int to = string.indexOf(end);
        return string.substring(from, to);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static void main(String[] args) {
        String text1="<wsnt:Notify xmlns:wsnt=\"http://docs.oasis-open.org/wsnMgr/b-2\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">\n" +
            "<wsnt:ConsumerReference>\n" +
            " <wsa:Address><topic>event</topic>\n" +
            " <message>\n" +
            "<resource>"+
            "<tablename>telemetry</tablename>"+
            "<siteName>西安</siteName>" +
            "<deviceName>光频传递收发设备</deviceName>" +
            "<dataName>接收误差信号采样值1</dataName>" +
            "</resource>" +
            "<value>" +
            "<detected_value>1</detected_value>" +
            "<timestamp>2020/04/27 15:11:28.277</timestamp>" +
            "</value>"+
            " </message>\n" +
            " </wsa:Address></wsnt:ConsumerReference><wsnt:Filter><wsnt:TopicExpression Dialect=\"http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple\">\n" +
            " </wsnt:TopicExpression></wsnt:Filter>\n" +
            " <wsnt:SubscriberAddress></wsnt:SubscriberAddress></wsnt:Notify>\n" +
            "\n" ;

            new UserNotificationProcessImpl().notificationProcess(text1);
    }
}


