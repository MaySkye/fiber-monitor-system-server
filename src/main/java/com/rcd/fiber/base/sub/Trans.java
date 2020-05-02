package com.rcd.fiber.base.sub;

import lombok.Data;
import com.rcd.fiber.base.soap.SendWSNCommand;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;

import javax.xml.ws.Endpoint;

/**
 * 用户向wsn层发送、接收消息，消息的格式为：
 * subscribe-address-topic
 */

@Data
public class Trans {

	private String receivAddr;
	private String wsnAddress;
	public String topic;
	public static SendWSNCommand recv;

	//设置用户id
	public static final String id = String.valueOf(System.currentTimeMillis());

	public Trans(String receiveAddr,String wsnAddr,String topic)
	{
		this.receivAddr = receiveAddr;
		this.wsnAddress = wsnAddr;
		this.topic = topic;
	}
	public void subscribe()
	{
		UserNotificationProcessImpl implementor = new UserNotificationProcessImpl();
		recv = new SendWSNCommand(this.receivAddr,this.wsnAddress);
		Endpoint endpint = Endpoint.publish(this.receivAddr, implementor);
		recv.subscribe(id, this.topic);
	}



	public void sendMethod(String mes) {
//		System.out.println(i++ + "\t" + mes.length() + "\t" + send.reliableNotify(sendTopic, mes, false, "A"));
	}



}
