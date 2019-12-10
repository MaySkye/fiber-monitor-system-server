package com.rcd.fiber.base.wsn;

import javax.xml.ws.Endpoint;

/**
 * @Author: HUHU
 * @Date: 2019/6/19 15:33
 */
public class testDemo1 {
    // 接收订阅消息Demo
    public static void main(String[] args) {
        //本机地址
        String wsnAddr = "http://192.168.99.121:9011/wsn-core";
        String receiveAddr = "http://192.168.99.18:9008/wsn-subscribe";
        SendWSNCommand receive = new SendWSNCommand(receiveAddr, wsnAddr);

        //设置用户id
        String id = "Fiber1";
        // 消息处理逻辑
        UserNotificationProcessImpl implementor = new UserNotificationProcessImpl();
        // 启接收服
        Endpoint endpint = Endpoint.publish(receiveAddr, implementor);
        receive.subscribe(id, "test2");


    }

}
