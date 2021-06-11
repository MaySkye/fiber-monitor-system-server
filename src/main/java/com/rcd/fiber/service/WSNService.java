package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.start.PublisherHandler;
import com.rcd.fiber.config.exceptionHandler.PermissionException;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.web.rest.auth.VerifyIdentity;
import com.rcd.fiber.web.rest.vm.LoginVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class WSNService {
    @Autowired
    PublisherHandler publisherHandler;

    public JSONObject sendControlInfo(JSONObject params) throws Exception {
        // 尝试解析Token
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token_base64 = request.getHeader("Authorization").substring(7);
        String username = TokenProvider.getClaims(token_base64).getSubject();
        // 验证pem证书
        String pemPath = LoginVM.savePemFile(params.getString("pemFileContent"), username);
        try {
            String strRes = VerifyIdentity.VerifyIdentity(username, pemPath);
            JSONObject jsonRes = JSONObject.parseObject(strRes);
            String code = jsonRes.getString("code");
            if (!"0".equals(code)) {
                throw new PermissionException(PermissionException.TYPE_AUTH_FAIL, "认证失败");
            }
        } catch (PermissionException e) {
            throw e;
        } catch (Exception e) {
            throw new PermissionException(PermissionException.TYPE_AUTH_FAIL, e.getMessage());
        }
        // 发送遥控命令
        return publisherHandler.sendControlInfo(params.getString("xml"));
    }
}
