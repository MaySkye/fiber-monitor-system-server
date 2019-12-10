package com.rcd.fiber.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Author: HUHU
 * @Date: 2019/6/18 10:00
 */
// @Configuration // SpringBoot启动将该类作为配置类，同配置文件一起加载
public class DataSourceConfig {
    @Bean(name="primaryDataSource") // 将该实体注入到IOC容器中
    @Qualifier("primaryDataSource") // 指定数据源名称，与Bean中的name属性原理相同，主要是为了确保注入成功
    @Primary // 指定主数据源
    @ConfigurationProperties(prefix="spring.datasource.primary") // 将配置文件中的数据源读取进到方法中，进行build
    public DataSource primaryDataSource() {
        System.out.println("-------------------- primaryDataSource init ---------------------");
        return DataSourceBuilder.create().build();
    }

    @Bean(name="voltdbDataSource")
    @Qualifier("voltdbDataSource")
    @ConfigurationProperties(prefix="spring.datasource.voltdb")
    public DataSource secondaryDataSource() {
        System.out.println("-------------------- voltdbDataSource init ---------------------");
        return DataSourceBuilder.create().build();
    }

}
