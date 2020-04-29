package com.rcd.fiber.base.wsn;


public class Trans {
    public static SendWSNCommandWSSyn register;
    public static SendWSNCommandWSSyn send;
//	public String publishAddress = "";
//	public String sendAddr;
//	public String wsnAddr;
//	public String topic;

//	private String id  = "";

    //设置用户id
//	public static final String id = String.valueOf(System.currentTimeMillis());

    public String regester(String wsnAddr, String sendAddr, String id, String topic) {
        register = new SendWSNCommandWSSyn(sendAddr, wsnAddr);
        String publishAddress = register.register(id, topic, sendAddr);
        return publishAddress;
    }

    public int sendMethod(String msg, String publishAddress, String sendAddr, String id, String topic) {
        if (!publishAddress.equals("")) {
            send = new SendWSNCommandWSSyn(sendAddr, publishAddress);
            return send.publish(id, topic, msg);
        } else {
            System.out.println("用户还未获得发布地址，无法发布！");
            return -1;
        }
    }

//	/**
//	 * 文本发送测试，单个文本大小为1024B
//	 * @param num
//	 */
//	public void sendTest(int num,String topic) {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < 1024-13-4; i++) {
//			sb.append('a');
//		}
//		String msg = sb.toString();
//		send = new SendWSNCommandWSSyn(sendAddr, publishAddress);
//		//发三倍，防止丢包
//		for (int i = 0; i < 256 * 1.5; i++) {
//			send.publish(id, topic, i + ":" + System.currentTimeMillis() + ":" + msg);
//		}
//		System.out.println("over");
//	}
//
//	public void sendTestWithSleep(int num,String topic) {
//		if (!publishAddress.equals("")) {
//			send = new SendWSNCommandWSSyn(sendAddr, publishAddress);
//		}else {
//			System.out.println("用户还未获得发布地址，无法发布！");
//		}
//		send.publish(id, topic, String.valueOf(System.currentTimeMillis()));
////		StringBuilder sb = new StringBuilder();
////		for (int i = 0; i < 1024-13-4; i++) {
////			sb.append('a');
////		}
////		String msg = sb.toString();
////		//发三倍，防止丢包
////		long startTime = System.currentTimeMillis(), endTime;
////		for (int i = 0; i < num * 1.5; i++) {
////			send.publish(id, sendTopic, i + ":" + System.currentTimeMillis() + ":" + msg);
////			endTime = System.currentTimeMillis();
////			if (endTime - startTime > 1000) {
////				try {
////					Thread.sleep(endTime - startTime);
////					startTime = System.currentTimeMillis();
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
////			}
////		}
//		System.out.println("over");
//	}

}
