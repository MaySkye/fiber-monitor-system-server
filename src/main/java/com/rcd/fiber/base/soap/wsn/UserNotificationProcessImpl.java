package com.rcd.fiber.base.soap.wsn;

import com.rcd.fiber.base.soap.INotificationProcess;
import com.rcd.fiber.service.dto.EventInfoDTO;
import com.rcd.fiber.web.rest.WSNResource;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 客户端ws处理程序
 * 对应的是wsn层ws处理程序
 */
@WebService(endpointInterface= "com.rcd.fiber.base.soap.INotificationProcess",
        serviceName="NotificationProcessImpl")
public class UserNotificationProcessImpl implements INotificationProcess {

    public UserNotificationProcessImpl() { }

    @Override
    public String notificationProcess(String notification) {

        String topic = splitString(notification, "<topic>", "</topic>");
        String msg = splitString(notification, "<message>", "</message>");
        String eventType = null,eventLevel = null,siteName = null,deviceName = null,dataName = null,value = null,timestamp = null;
        try {
            // 创建SAXReader对象
            SAXReader reader = new SAXReader();
            reader.setEncoding("utf-8");
            // 读取XML文件结构
            Document doc = DocumentHelper.parseText("<message>"+msg+"</message>");;
            // 获取XML文件根节点
            Element root = doc.getRootElement();
            Element n1,n2,n3;
            for(Iterator i=root.elementIterator("event");i.hasNext();){
                n1=(Element)i.next();
                eventType=n1.elementText("eventType");
                eventLevel=n1.elementText("eventLevel");
            }
            for(Iterator i=root.elementIterator("resource");i.hasNext();){
                n2=(Element)i.next();
                siteName=n2.elementText("siteName");
                deviceName=n2.elementText("deviceName");
                dataName=n2.elementText("dataName");
            }
            for(Iterator i = root.elementIterator("value"); i.hasNext();){
                n3=(Element)i.next();
                value=n3.elementText("value");
                timestamp=n3.elementText("timestamp");
                //timestamp= timestamp.replace('/','-');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventInfoDTO eventInfoDTO = new EventInfoDTO(siteName,deviceName,dataName,eventType,eventLevel,value,timestamp);
        System.out.println("eventInfoDTO：" + eventInfoDTO.toString());
        //WSNResource.lock.lock();
        List<EventInfoDTO> list = WSNResource.eventInfoDTOMap.get(siteName);
        if(list !=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getSiteName().equals(siteName)&&list.get(i).getDeviceName().equals(deviceName)&&list.get(i).getDataName().equals(dataName)){
                    list.remove(list.get(i));
                    break;
                }
            }
            list.add(eventInfoDTO);
        }else{
            list = new LinkedList<>();
            list.add(eventInfoDTO);
            WSNResource.eventInfoDTOMap.put(siteName, list);
        }

        //WSNResource.lock.unlock();

        //System.out.println("消息：" + notification);
        return notification;
    }

    public String splitString(String string, String start, String end)
    {
        int from = string.indexOf(start) + start.length();
        int to = string.indexOf(end);
        return string.substring(from, to);
    }

    public static void main(String[] args) {
        String text1 = "<topic>event</topic>";
        String text= "<wsnt:Notify xmlns:wsnt=\"http://docs.oasis-open.org/wsnMgr/b-2\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsnt:ConsumerReference> <wsa:Address>\n" +
            "            <topic>event</topic>\n" +
            "            <message>\n" +
            "            <event>\n" +
            "            <eventType>over_lower_limit</eventType>\n" +
            "            <eventLevel>alarm</eventLevel>\n" +
            "            </event>\n" +
            "            <resource>\n" +
            "            <siteName>西安</siteName>\n" +
            "            <deviceName>光频传递收发设备</deviceName>\n" +
            "            <dataName>接收误差信号采样值9</dataName>\n" +
            "            </resource>\n" +
            "            <value>\n" +
            "            <value>-20.0</value>\n" +
            "            <timestamp>2020/05/17 11:03:45.582</timestamp>\n" +
            "            </value>\n" +
            "            </message>\n" +
            "            </wsa:Address></wsnt:ConsumerReference><wsnt:Filter><wsnt:TopicExpression Dialect=\"http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple\"></wsnt:TopicExpression></wsnt:Filter><wsnt:SubscriberAddress></wsnt:SubscriberAddress></wsnt:Notify>";

            new UserNotificationProcessImpl().notificationProcess(text);
    }
}


