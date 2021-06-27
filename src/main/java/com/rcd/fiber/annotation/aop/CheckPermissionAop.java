package com.rcd.fiber.annotation.aop;

import com.alibaba.fastjson.JSON;
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
import java.io.IOException;
import java.io.PrintWriter;

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
            String username = TokenProvider.getClaims(token_base64).getSubject();
            // 检查权限
            checkPermission(username, request, checkPermission);
        }
        try {
            proceedingJoinPoint.proceed(); //执行程序
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @AfterThrowing(value = "pointcut() && @annotation(checkPermission)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, CheckPermission checkPermission, Exception ex) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 返回信息
        JSONObject res = new JSONObject();
        // 设置标题
        res.put("title", ex.getMessage());
        // 尝试获取原因
        if (ex.getCause() != null) {
            res.put("detail", ex.getCause().getMessage());
        }
        // 写入异常类型
        res.put("type", ex.getClass().getSimpleName());
        // 写出信息
        response.setCharacterEncoding("utf-8");
        response.setStatus(504);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(res));
        writer.close();
        WWLogger.error("【异常】【CheckPermission.afterThrowing切面方法】请求：" + ex.getMessage());
        ex.printStackTrace();
    }

    /**
     * 检查权限
     *
     * @param username
     * @param request
     * @param checkPermission
     * @throws Exception
     */
    private void checkPermission(String username, HttpServletRequest request, CheckPermission checkPermission) throws Exception {
        // 获取参数
        String objectId = request.getParameter("objectId");
        String siteName = request.getParameter("site_name");
        String department = request.getParameter("department");
        // 根据objectId：获取组态图、删除组态图时，查询mxe文件所需的分系统权限
        if (objectId != null) {
            MxeFileInfo info = mongoService.getMxeFileInfoByObjectId(objectId);
            checkMxeFilePermission(username, checkPermission, info);
        }
        // 根据站点名：获取组态图时，查询mxe文件所需的分系统权限
        else if (siteName != null) {
            MxeFileInfo info = mongoService.getMxeFileInfoBySite(siteName);
            checkMxeFilePermission(username, checkPermission, info);
        }
        // 上传组态图时，检查分系统权限
        else if (department != null) {
            if (checkPermission.checkDepartment()) {
                checkResponse(Check.Check(policyName, username, monitorGraphResource + "/" + department, checkPermission.action(), "", username, UserJWTController.userMd5Map.get(username)));
            }
        }
    }

    /**
     * 检查mxe文件权限
     *
     * @param username
     * @param checkPermission
     * @param mxeInfo
     * @throws Exception
     */
    private void checkMxeFilePermission(String username, CheckPermission checkPermission, MxeFileInfo mxeInfo) throws Exception {
        if (mxeInfo == null) {
            throw new Exception("找不到组态图", new Throwable("MxeFile not found"));
        }
        if (checkPermission.checkDepartment()) {
            checkResponse(Check.Check(policyName, username, monitorGraphResource + "/" + mxeInfo.getMetadata().getString("department"), checkPermission.action(), "", username, UserJWTController.userMd5Map.get(username)));
        }
        if (checkPermission.checkSite()) {
            checkResponse(Check.Check(policyName, username, monitorGraphResource + "/" + mxeInfo.getMetadata().getString("site_name"), checkPermission.action(), "", username, UserJWTController.userMd5Map.get(username)));
        }
    }

    /**
     * 检查安全系统响应
     *
     * @param res
     * @throws Exception
     */
    private void checkResponse(String res) throws Exception {
        JSONObject result = JSONObject.parseObject(res);
        if (!"0".equals(result.getString("code"))) {
            {
                throw new PermissionException("您无权查看", "请联系管理员！");
            }
        }
    }
}
