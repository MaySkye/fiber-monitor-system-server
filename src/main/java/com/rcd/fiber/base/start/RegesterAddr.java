package com.rcd.fiber.base.start;

import com.rcd.fiber.base.wsn.Trans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: HUHU
 * @Date: 2019/6/28 16:03
 * 注册发布主题
 */
@Component
public class RegesterAddr {
    static Logger logger = LoggerFactory.getLogger(RegesterAddr.class);
    private static String addr;
    private static Trans trans;
    private static String parameter1;
    private static String parameter2;
    // 状态
    @Value("${wsn.send.status}")
    private  String status;
    // 从配置文件取地址
    @Value("${wsn.send.wsnAddr1}")
    private String str1;
    @Value("${wsn.send.sendAddr2}")
    private String str2;

    public static String getAddr() {
        return addr;
    }

    public static Trans getTrans() {
        return trans;
    }

    @PostConstruct
    public void setAddrValue() {
        setParameter1();
        setParameter2();
        if (addr == null) {
            trans = new Trans();
            addr = "dddd";
            if ( !status.isEmpty()&&status.equals("1")) {
               addr = trans.regester(parameter1, parameter2, "admin", "test3");
            }

        }
        logger.info(parameter1);
        logger.info(parameter2);
        logger.info(status);
        logger.info("获取服务地址" + addr);

    }

    private void setParameter1() {
        RegesterAddr.parameter1 = str1;
    }

    private void setParameter2() {
        RegesterAddr.parameter2 = str2;
    }


}
