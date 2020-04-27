package com.rcd.fiber.annotation.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.annotation.CheckPermission;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.web.rest.UserJWTController;
import com.rcd.fiber.web.rest.auth.Check;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Aspect
@Component
public class CheckPermissionAop {

    @Pointcut(value = "@annotation(com.rcd.fiber.annotation.CheckPermission)")
    private void pointcut() {
        System.out.println("进入切面方法");
    }

    // 根据目标方法返回值是否为JSONArray，来决定返回值是JSONObject或JSONArray
    @Around(value = "pointcut() && @annotation(checkPermission)")
    public void around(ProceedingJoinPoint proceedingJoinPoint, CheckPermission checkPermission) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String token_base64 = request.getHeader("Authorization").substring(7);
        String subject = TokenProvider.getClaims(token_base64).getSubject();
        if(checkPermission.value())
        {
            try{
                JSONObject jsonObject = Check.doCheck(subject, checkPermission.object(), checkPermission.action(), UserJWTController.userMd5Map.get(subject));
                if(!"0".equals(jsonObject.getString("code")))
                    return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }
        try {
            proceedingJoinPoint.proceed(); //执行程序
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @AfterThrowing(value = "pointcut() && @annotation(checkPermission)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, CheckPermission checkPermission, Exception ex) {
        System.out.println("【异常】【CheckPermission.afterThrowing切面方法】请求：" + checkPermission.value());
        ex.printStackTrace();
    }

}
