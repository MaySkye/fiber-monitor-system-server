package com.rcd.fiber.base.pub;

import com.rcd.fiber.base.soap.SendWSNCommandWSSyn;

public class Trans {
	public static SendWSNCommandWSSyn register;

	public static SendWSNCommandWSSyn send;

	public String topic;
	public String sendAddr;
	public String wsnAddr;


	public String publishAddress = "";

	private static int i = 0;

	//设置用户id
	public static final String id = String.valueOf(System.currentTimeMillis());

	public void register()
	{
		register = new SendWSNCommandWSSyn(sendAddr, wsnAddr);
		publishAddress = register.register(id, topic, sendAddr);
		System.out.println(publishAddress);
	}

	public Trans(String wsnAddr,String sendAddr,String sendTopic)
	{
		this.wsnAddr = wsnAddr;
		this.sendAddr = sendAddr;
		this.topic = sendTopic;
		register = new SendWSNCommandWSSyn(sendAddr, wsnAddr);
		publishAddress = register.register(id, topic, this.sendAddr);
	}
	public Trans() {
		register = new SendWSNCommandWSSyn(sendAddr, wsnAddr);
//		SendNotificationProcessImpl impl = new SendNotificationProcessImpl();
//		Endpoint.publish(sendAddr, impl);
		//publishAddress = register.register(id, sendTopic, sendAddr);
		//System.out.println(publishAddress);
	}

	public void sendMethod(String msg) {
		if (!publishAddress.equals("")) {
			send = new SendWSNCommandWSSyn(sendAddr, publishAddress);
			send.publish(id, topic, msg);
		}else {
			System.out.println("用户还未获得发布地址，无法发布！");
		}
	}

	/**
	 * 文本发送测试，单个文本大小为1024B
	 * @param num
	 */

}
