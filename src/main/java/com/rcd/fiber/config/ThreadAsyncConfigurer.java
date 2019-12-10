package com.rcd.fiber.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author: HUHU
 * @Date: 2019/6/27 18:18
 */
@Configuration
@EnableAsync//开启对异步任务的支持
public class ThreadAsyncConfigurer {
}
