package com.rcd.fiber.config.exceptionHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.utils.WWLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常捕获
 */
@ControllerAdvice
public class MyExceptionHandler {
    @Value("${my-logging.exception-handler.printStackTrace}")
    boolean printStackTrace;

    /**
     * 异常拦截方法
     *
     * @param request
     * @param response
     * @param e
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        // 打印Exception Message
        WWLogger.error("MyExceptionHandler拦截", e.getClass().getSimpleName(), e.getMessage());
        // 是否打印异常堆栈
        if (printStackTrace) e.printStackTrace();
        // 设定授权错误状态码
        handleDifferentException(request, response, e);
    }

    /**
     * 根据不同异常，设置状态码
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    public void handleDifferentException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        // 设置默认状态码
        response.setStatus(500);
        JSONObject res = new JSONObject();
        // 设置标题
        res.put("title", e.getMessage());
        // 尝试获取原因
        if (e.getCause() != null) {
            res.put("detail", e.getCause().getMessage());
        }
        // 写入异常类型
        res.put("type", e.getClass().getSimpleName());
        // 授权异常
        if (e instanceof PermissionException) {
            response.setStatus(401);
        }
        // 数据库约束异常
        else if (e instanceof DataIntegrityViolationException) {
            response.setStatus(504);
            res.put("title", "数据库约束异常");
            res.put("tip", "提示：<br/>（1）当前项目存在子元素，无法删除；<br/>（2）重名；");
        }
        // 写入响应
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSON.toJSONString(res));
    }
}
