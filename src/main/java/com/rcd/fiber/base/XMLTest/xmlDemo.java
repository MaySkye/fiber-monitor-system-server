package com.rcd.fiber.base.XMLTest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: HUHU
 * @Date: 2019/6/26 14:24
 */
public class xmlDemo {

    static Logger logger = LoggerFactory.getLogger(xmlDemo.class);

    public static void main(String[] args) {
        String str = "<sendTime>383:1561602252262</sendTime>" +
            "<resource><site_name>siteName</site_name><device_name>device_name</device_name><resource_name>data_name</resource_name><timestamp>timestamp</timestamp></resource><alarm><value>fValue</value><fault_type>fault_type</fault_type><upper_limit>upper_limit</upper_limit><lower_limit>lower_limit</lower_limit></alarm>\n";
        Boolean b = readXml(str);
    }

    public static Boolean readXml(String notification) {
        System.out.println("接收到来自发布订阅系统的消息：" + notification);
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
//            "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">" + notification + "</xs:schema>";
//        try {
//            Document document = DocumentHelper.parseText(xml);
//
//            Element root = document.getRootElement(); // 获取根节点
//            logger.info("根节点：" + root.getName()); // 拿到根节点的名称
//            //获取子节点
//            String date = root.elementText("sendTime");//获取子节点
//            logger.info("Date:" + date);
//            // 获取设备节点
//            Element resource = root.element("resource");
//            // 获取设备名称
//            String siteName = resource.elementText("site_name");
//            logger.info("siteName: " + siteName);
//            String device_name = root.elementText("device_name");//获取子节点
//            logger.info("device_name:" + device_name);
//
//
//
//            String errorsInfo = root.elementText("ErrorsInfo");//获取子节点
//            String message = root.elementText("Message");//获取子节点
//            String serialNo = root.elementText("SerialNo");//获取子节点
//
//
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
