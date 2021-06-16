package com.rcd.fiber.base.soap.wsn;

import com.rcd.fiber.base.soap.INotificationProcess;
import com.rcd.fiber.base.start.NotificationHandler;
import com.rcd.fiber.service.dto.EventInfoDTO;
import com.rcd.fiber.utils.WWLogger;
import com.rcd.fiber.web.rest.WSNResource;
import com.rcd.fiber.websocket.EventPublisher;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.jws.WebService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 客户端ws处理程序
 * 对应的是wsn层ws处理程序
 */
@WebService(endpointInterface = "com.rcd.fiber.base.soap.INotificationProcess",
    serviceName = "NotificationProcessImpl")
public class UserNotificationProcessImpl implements INotificationProcess {

    public static NotificationHandler notificationHandler = new NotificationHandler();


    public UserNotificationProcessImpl() {
    }

    @Override
    /**
     * 中间件函数：拦截并对notification进行预处理
     */
    public synchronized String notificationProcess(String notification) {
        String msg = getValueFromXMLFragment(notification, "message");
        String eventType = null, eventLevel = null, siteName = null, deviceName = null, dataName = null, value = null, timestamp = null;
        try {
            // 创建SAXReader对象
            SAXReader reader = new SAXReader();
            reader.setEncoding("utf-8");
            // 读取XML文件结构
            Document doc = DocumentHelper.parseText("<message>" + msg + "</message>");
            // 获取XML文件根节点
            Element root = doc.getRootElement();
            for (Iterator i = root.elementIterator("event"); i.hasNext(); ) {
                Element n1 = (Element) i.next();
                eventType = n1.elementText("eventType");
                eventLevel = n1.elementText("eventLevel");
            }
            for (Iterator i = root.elementIterator("resource"); i.hasNext(); ) {
                Element n2 = (Element) i.next();
                siteName = n2.elementText("siteName");
                deviceName = n2.elementText("deviceName");
                dataName = n2.elementText("dataName");
            }
            for (Iterator i = root.elementIterator("value"); i.hasNext(); ) {
                Element n3 = (Element) i.next();
                value = n3.elementText("value");
                timestamp = n3.elementText("timestamp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建提醒事件对象
        EventInfoDTO eventInfoDTO = new EventInfoDTO(siteName, deviceName, dataName, eventType, eventLevel, value, timestamp);
        notificationHandler.addNewEvent(eventInfoDTO);
        // 广播事件
        EventPublisher.sendEvent(eventInfoDTO);
        return notification;
    }

    /**
     * 从xml片段中获取指定信息
     *
     * @param xml
     * @param key
     * @return
     */
    public static String getValueFromXMLFragment(String xml, String key) {
        int begin = xml.indexOf("<" + key + ">");
        int end = xml.indexOf("</" + key + ">");
        if (begin != -1 && end != -1) {
            return xml.substring(begin + key.length() + 2, end);
        } else return null;
    }
}


