package com.rcd.fiber.base.wsn;

import java.io.DataInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dongpeifan on 2019/6/3.
 */
public class TelemetryData {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static String addr = "";
    private static Trans trans = null;

    static {
        trans = new Trans();
        addr = trans.regester("http://192.168.100.11:9011/wsn-core", "http://192.168.100.12:9199/wsn-send"
            , "admin", "test1");
    }

    private DataInputStream dis;
    private String device_address;
    private int data_num;
    private String timestamp;
    private double value;
//    private static String sendAddr = "http://127.0.0.1:9199/wsn-send";
//    private static String wsnAddr = "http://192.168.100.11:9000/wsn-core";
//    private Trans pub = new Trans(wsnAddr,sendAddr,"admin","test1");

//    private static DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
//    private static DocumentBuilder domBuilder;

//    static {
//        try {
//            domBuilder = domFactory.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//    }


    public TelemetryData(DataInputStream is, int data_num, String ip) {
        try {
            byte[] data = new byte[4];
            for (int i = 0; i < 4; i++) {
                data[i] = is.readByte();
            }
            // value = Float.intBitsToFloat((is.readByte() & 0xff) | ((is.readByte() & 0xff) << 8) | ((is.readByte() & 0xff) << 16) | ((is.readByte() & 0xff) << 24));
            value = analyzeData(data);
            System.out.println("value:" + value);
            this.data_num = data_num;
            this.device_address = ip;
            Date date = new Date();
            this.timestamp = sdf.format(date);
            String sql = "Select * from data_info where data_num=" + data_num;
//            ResultSet resultSet = Jdbcdemo.execute1(sql);

            String deviceType, type, upperLimit, lowerLimit, data_name;

//            resultSet.next();
//            //    String deviceName = resultSet.getString("device_name");
//
//            deviceType = resultSet.getString("device_type");
//            type = resultSet.getString("type");
//            upperLimit = resultSet.getString("upper_limit");
//            lowerLimit = resultSet.getString("lower_limit");
//            data_name = resultSet.getString("data_name");
//
//            String data_table = resultSet.getString("data_table");
            String msg =
                //    "<resource>" +
                "<site_name>" + "xian" + "</site_name>" +
                    "<device_name>" + "deviceType" + "</device_name>" +
                    "<data_name>" + "data_name" + "</data_name>" +
                    "<timestamp>" + timestamp + "</timestamp>" +
                    "<detected_value>" + value + "</detected_value>" +
                    "<data_table>" + "telemetry" + "</data_table>";
            //    "</resource>" +
            //   "<value>" +
            //   "<value>" + value + "</value>" ;
//                        "<type>" + type + "</type>" +
//                        "<upper_limit>" + upperLimit + "</upper_limit>" +
//                        "<lower_limit>" + lowerLimit + "</lower_limit>" +
            // "</value>";
            System.out.println(msg);
            trans.sendMethod(msg, addr, "http://192.168.100.12:9199/wsn-send", "admin", "test1");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double analyzeData(byte[] data) {
        double value = 1.0;
        try {
            String str = data2BinaryStr(data);

            //符号位
            if (str.charAt(0) == '1') {
                value *= -1;
            }

            //指数位
            String indexStr = str.substring(1, 9);
            int index = 0;
            for (int i = 0; i < 8; i++) {
                index += (Math.pow(2, 7 - i) * (indexStr.charAt(i) - '0'));
            }
            index -= 127;

            //尾数位
            String mantissaStr = str.substring(9);
            double mantissa = 0;
            for (int i = 0; i < 23; i++) {
                mantissa += (Math.pow(2, -i - 1) * (mantissaStr.charAt(i) - '0'));
            }

            value += mantissa;
            value *= Math.pow(2, index);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String data2BinaryStr(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String temp = Long.toString(data[i] & 0xff, 2);
            if (temp.length() < 8) {
                for (int j = 0; j < 8 - temp.length(); j++) {
                    sb.append("0");
                }
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    public String getDevice_address() {
        return device_address;
    }

    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

    public int getData_num() {
        return data_num;
    }

    public void setData_num(int data_num) {
        this.data_num = data_num;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

//    public static void main(String[] args) {
//        byte[] data = {(byte) 64, (byte)-96, (byte) 0, (byte) 0 };
//        System.out.print(analyzeData(data));
//    }
}
