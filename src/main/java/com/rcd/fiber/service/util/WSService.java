package com.rcd.fiber.service.util;

import com.rcd.fiber.base.start.RegesterAddr;
import com.rcd.fiber.base.wsn.SendWSNCommand;
import com.rcd.fiber.base.wsn.Trans;
import com.rcd.fiber.base.wsn.UserNotificationProcessImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Endpoint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: HUHU
 * @Date: 2019/6/27 23:56
 *
 * 发布与订阅主题
 */
@Service
@Transactional
public class WSService {
    private final Logger logger = LoggerFactory.getLogger(WSService.class);
    private String addr;

    //对应application-dev.yml配置文件
    @Value("${wsn.receive.wsnAddr1}")
    private String receiveParm1;
    @Value("${wsn.receive.receiveAddr2}")
    private String receiveParm2;
    // 发布的时候
    @Value("${wsn.send.sendAddr3}")
    private String sendParm1;

    // 获取地址
    private String getAddr(){
        // 将地址的声明为全局变量自动获取
        if (this.addr == null){
            this.addr = RegesterAddr.getAddr();
        }
        return addr;
    }


    // 获取订阅消息
    @Async
    public void  getInfoByWSN(){
        //本机地址
        String wsnAddr = receiveParm1;
        String receiveAddr = receiveParm2;
        SendWSNCommand receive = new SendWSNCommand(receiveAddr, wsnAddr);
        //设置用户id
        String id = "Fiber";
        // 消息处理逻辑
        UserNotificationProcessImpl implementor = new UserNotificationProcessImpl();
        // 启接收服
        Endpoint endpint = Endpoint.publish(receiveAddr, implementor);
        receive.subscribe(id, "test2");
    }

    // 发布主题，关闭设备
    @Async
    public void sendInfoByWSN(String site_name,String device_name,String data_name){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timestamp;
        double value = 1; //value=1表示关闭设备
        Trans trans = RegesterAddr.getTrans();
        // value = analyzeData(data);
        Date date = new Date();
        System.out.println("value:" + value);
        timestamp = sdf.format(date);
        logger.info(getAddr());
        if (!getAddr().isEmpty()) {
            String msg =
                "<site_name>" + site_name+ "</site_name>" +
                    "<device_name>" + device_name + "</device_name>" +
                    "<data_name>" + data_name+ "</data_name>" +
                    "<timestamp>" + timestamp + "</timestamp>" +
                    "<detected_value>" + value + "</detected_value>" +
                    "<data_table>" + "telemetry" + "</data_table>";
            System.out.println(msg);
            trans.sendMethod(msg, getAddr(), sendParm1, "admin", "test3");
        }
    }

}
