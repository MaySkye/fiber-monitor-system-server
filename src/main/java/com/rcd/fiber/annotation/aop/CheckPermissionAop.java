package com.rcd.fiber.annotation.aop;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.annotation.CheckPermission;
import com.rcd.fiber.config.exceptionHandler.PermissionException;
import com.rcd.fiber.domain.entity.MxeFileInfo;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.service.MongoService;
import com.rcd.fiber.utils.WWLogger;
import com.rcd.fiber.web.rest.UserJWTController;
import com.rcd.fiber.web.rest.auth.Check;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class CheckPermissionAop {

    @Autowired
    private MongoService mongoService;

    @Value("${auth.monitorGraphResource}")
    private String monitorGraphResource = "";


    public String policyName = "";

    @Value("${auth.policyName}")
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @Pointcut(value = "@annotation(com.rcd.fiber.annotation.CheckPermission)")
    private void pointcut() {
        System.out.println("进入切面方法");
    }

    @PostConstruct
    public void printLog() {
        WWLogger.info("CheckPermissionAop：monitorGraphResource：" + monitorGraphResource);
        WWLogger.info("CheckPermissionAop： policyName：" + policyName);
    }

    // 根据目标方法返回值是否为JSONArray，来决定返回值是JSONObject或JSONArray
    @Around(value = "pointcut() && @annotation(checkPermission)")
    public void around(ProceedingJoinPoint proceedingJoinPoint, CheckPermission checkPermission) throws Exception {
        if (checkPermission.value()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            // 尝试解析Token
            String token_base64 = request.getHeader("Authorization").substring(7);
            String user = TokenProvider.getClaims(token_base64).getSubject();
            // 注解信息
            String targetObject = checkPermission.object();
            String action = checkPermission.action();
            // 检查分系统
            if (checkPermission.checkDepartment()) {
                String md5 = request.getParameter("md5");
                String siteLevel = request.getParameter("site_level");
                String siteName = request.getParameter("site_name");
                if (md5 != null) {
                    MxeFileInfo info = mongoService.getMxeFileInfoByMd5(md5);
                    targetObject = info.getMetadata().getString("department");
                } else if (siteLevel != null && siteName != null) {
                    MxeFileInfo info = mongoService.getMxeFileInfoBySiteNameAndLevel(siteName, siteLevel);
                    targetObject = info.getMetadata().getString("department");
                } else if (request.getParameter("department") != null) {
                    targetObject = request.getParameter("department");
                }
            }
            // 检查权限
            String check = Check.Check(policyName, user, monitorGraphResource + "/" + targetObject, action, "", user, UserJWTController.userMd5Map.get(user));
            JSONObject res = JSONObject.parseObject(check);
            if (!"0".equals(res.getString("code")))
                throw new PermissionException("您无权查看", "请联系管理员！");
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
