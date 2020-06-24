package com.rcd.fiber.base.sub; /**
 * Created by IntelliJ IDEA.
 * User: 15373
 * Date: 2020/4/1
 */

import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.service.WSNService;

import java.nio.file.WatchService;

/**
 * Created by-logan on 2020/4/1.
 */
public class CaseSubscirbe {
    private Trans trans;
	//wsn程序中的wsn地址，此处为默认值
    public static String wsnAddr = "http://192.168.253.1:9011/wsn-core";
	//保证端口要和其他订阅程序不一致，此处为默认值
	//接受的消息在soap包的UserNotificationProcessImpl.java的notificationProcess方法
    public static String sendAddr = "http://192.168.253.5:9039/wsn-subscribe";

    public CaseSubscirbe(String sendAddr,String wsnAddr,String topic)
    {
        trans = new Trans(sendAddr,wsnAddr,topic);
    }
    public void subscibe()
    {
        trans.subscribe();
    }

    public static void main(String[] args) {
		//CaseSubscirbe param 1:订阅地址 param 2：wsn地址 param 3:订阅主题名
        CaseSubscirbe sub = new CaseSubscirbe(sendAddr,wsnAddr,"event");
		//订阅主题
        sub.subscibe();
    /*    SendWSNCommand receive = new SendWSNCommand(sendAddr, wsnAddr);
        // 消息处理逻辑
        UserNotificationProcessImpl implementor = new UserNotificationProcessImpl();
        // 启接收服
        Endpoint endpint = Endpoint.publish(sendAddr, implementor);
        String info = receive.subscribe("kobe", "test");

        System.out.println("订阅消息："+implementor.getInfo());*/
    }
}
