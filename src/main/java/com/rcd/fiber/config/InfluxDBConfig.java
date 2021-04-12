package com.rcd.fiber.config;

import com.rcd.fiber.utils.WWLogger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
    @Value("${spring.influx.user}")
    private String user;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.url}")
    private String url;

    @Value("${spring.influx.database}")
    private String database;


    private InfluxDB build() {
        WWLogger.info("InfluxDB配置  ", "URL：" + url, "user：" + user, "password：" + password);
        InfluxDB influxDB = null;
        if (user == null || password == null) {
            influxDB = InfluxDBFactory.connect(this.url);
        } else {
            influxDB = InfluxDBFactory.connect(this.url, this.user, this.password);
        }
        influxDB.setDatabase(this.database);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }

    @Bean
    public InfluxDB influxDB() {
        return build();
    }
}
