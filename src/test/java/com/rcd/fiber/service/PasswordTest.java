package com.rcd.fiber.service;

import com.rcd.fiber.FiberMonitorSystemApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: HUHU
 * @Date: 2019/7/21 14:10
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FiberMonitorSystemApp.class)
public class PasswordTest {
    @Autowired
    public  PasswordEncoder passwordEncoder;

    @Test
    public void getPassword() {
        System.out.println(passwordEncoder.encode("zhao"));
        System.out.println(passwordEncoder.encode("kong"));
        System.out.println(passwordEncoder.encode("yang"));
    }

}
