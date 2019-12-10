package com.rcd.fiber.config;

/**
 * @Author: HUHU
 * @Date: 2019/7/16 13:39
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer
    extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/siteimgs/**").addResourceLocations("classpath:/static/siteimgs/");
        super.addResourceHandlers(registry);
    }

}
