package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import com.rcd.fiber.base.start.PublisherHandler;
import com.rcd.fiber.config.exceptionHandler.PermissionException;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.service.WSNService;
import com.rcd.fiber.web.rest.auth.VerifyIdentity;
import com.rcd.fiber.web.rest.vm.LoginVM;
import io.micrometer.core.annotation.Timed;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/wsn")
@CrossOrigin("*")
public class WSNResource {
    @Autowired
    private WSNService wsnService;

    /**
     * 发送遥控信息：修改设备监控值
     *
     * @return
     * @params 包含xml（遥控信息）、pemFileContent
     */
    @PostMapping("/sendControlInfo")
    @ResponseBody
    public JSONObject sendControlInfo(@RequestBody JSONObject params) throws Exception {
        // 发送遥控命令
        return wsnService.sendControlInfo(params);
    }
}
