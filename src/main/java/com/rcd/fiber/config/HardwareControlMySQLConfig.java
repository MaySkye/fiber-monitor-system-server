package com.rcd.fiber.config;

import com.rcd.fiber.utils.WWLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class HardwareControlMySQLConfig {

    @Value("${control.driver}")
    private String driver;

    @Value("${control.url}")
    private String url;

    @Value("${control.user}")
    private String user;

    @Value("${control.password}")
    private String password;


    public Connection getConnection() throws Exception {
        //加载驱动程序
        Connection con;
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
        if (!con.isClosed()) {
            WWLogger.info("【遥控控制】成功连接数据库!");
        } else {
            WWLogger.error("【遥控控制】连接数据库失败!");
        }
        return con;
    }
}
