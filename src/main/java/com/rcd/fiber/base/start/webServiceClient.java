package com.rcd.fiber.base.start;

import com.rcd.fiber.service.util.WSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: HUHU
 * @Date: 2019/6/27 18:53
 */

@Component
@ComponentScan
public class webServiceClient {
    static Logger logger = LoggerFactory.getLogger(webServiceClient.class);
    @Autowired
    WSService service;

    @Value("${wsn.receive.status}")
    private String status;

    /**
     * @Postcontruct’在依赖注入完成后自动调用
     */
    @PostConstruct
    public void start() {
        logger.info("启动wsn客户端，等待订阅消息");

        // service.getInfoByWSN();
        if (status.equals("1")) {
            service.getInfoByWSN();
        }
    }

}
