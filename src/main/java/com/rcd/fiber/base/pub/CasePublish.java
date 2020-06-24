package com.rcd.fiber.base.pub; /**
 * Created by IntelliJ IDEA.
 * User: 15373
 * Date: 2020/4/1
 */

/**
 * Created by-logan on 2020/4/1.
 */
public class CasePublish {
    private Trans trans;
	//wsn程序中的地址
    public static String wsnAddr = "http://192.168.253.1:9011/wsn-core";
	//sendAddr中保证不和其他发布程序的端口冲突
    public static String sendAddr = "http://192.168.253.5:9039/wsn-send";
    public CasePublish(String sendAddr,String wsnAddr,String topic)
    {
        trans = new Trans(sendAddr,wsnAddr,topic);
    }
    public void register()
    {
        trans.register();
    }
    public void publishmsg(String msg)
    {
        trans.sendMethod(msg);
    }

    public static void main(String[] args) {
		//CasePublish构造参数 param 1:wsn地址 param 2:发布地址 param 3:发布主题名
        CasePublish pub = new CasePublish(wsnAddr,sendAddr,"event");
		//发布主题
        pub.register();
		//发布消息
        String msg = "            <event>\n" +
            "            <eventType>over_lower_limit</eventType>\n" +
            "            <eventLevel>alarm</eventLevel>\n" +
            "            </event>\n" +
            "            <resource>\n" +
            "            <siteName>西安</siteName>\n" +
            "            <deviceName>光频传递收发设备</deviceName>\n" +
            "            <dataName>接收误差信号采样值5</dataName>\n" +
            "            </resource>\n" +
            "            <value>\n" +
            "            <value>-20.0</value>\n" +
            "            <timestamp>2020/05/17 11:03:45.582</timestamp>\n" +
            "            </value>\n" ;
        pub.publishmsg(msg);
    }
}
