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
    public static String wsnAddr = "http://127.0.0.1:9011/wsn-core";
	//sendAddr中保证不和其他发布程序的端口冲突
    public static String sendAddr = "http://127.0.0.1:9019/wsn-send";
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
        CasePublish pub = new CasePublish(wsnAddr,sendAddr,"control");
		//发布主题
        pub.register();
		//发布消息
        pub.publishmsg("<SYSTEM_CATE>41</SYSTEM_CATE>");
    }
}
