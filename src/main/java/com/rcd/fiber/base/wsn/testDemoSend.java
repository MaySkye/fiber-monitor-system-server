package com.rcd.fiber.base.wsn;

import java.io.DataInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: HUHU
 * @Date: 2019/6/20 14:56
 */
public class testDemoSend {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static Trans trans;
    private static String addr;
    private static String timestamp;
    private static double value = 1;

    // 初始化静态变量
    static {
        trans = new Trans();
        ///addr = "http://127.0.0.1:10000/wsn-publish";
         addr = trans.regester("http://127.0.0.1:9011/wsn-core", "http://127.0.0.1:9199/wsn-send"
            , "admin", "test3");
    }

    private DataInputStream dis;
    private String device_address;
    private int data_num;

    public static void main(String[] args) {
        // value = analyzeData(data);
        Date date = new Date();
        System.out.println("value:" + value);
        timestamp = sdf.format(date);
        System.out.println(addr);
        if (!addr.isEmpty()) {
            String msg =
                //    "<resource>" +
                "<site_name>" + "xian" + "</site_name>" +
                    "<device_name>" + "deviceType" + "</device_name>" +
                    "<data_name>" + "data_name" + "</data_name>" +
                    "<timestamp>" + timestamp + "</timestamp>" +
                    "<detected_value>" + value + "</detected_value>" +
                    "<data_table>" + "telemetry" + "</data_table>";
            System.out.println(msg);
            trans.sendMethod(msg, addr, "http://127.0.0.1:9199/wsn-send", "admin", "test3");
        }
    }

}
