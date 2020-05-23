package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rcd.fiber.security.jwt.JWTFilter;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.web.rest.auth.Common;
import com.rcd.fiber.web.rest.auth.VerifyIdentity;
import com.rcd.fiber.web.rest.errors.BadRequestAlertException;
import com.rcd.fiber.web.rest.vm.LoginVM;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public static HashMap<String, String> userMd5Map = new HashMap<>();

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 直接返回id-token
     */
    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<Object> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletRequest request) {
        // 若请求不来自http://localhost:9000/（后台管理项目），则验证私钥

        if(!"http://localhost:9000/".equals(request.getHeader("Referer")))
        {
            try {
                // 校验用户合法性
                String username = loginVM.getUsername();
                String pemPath = loginVM.savePemFile();
                System.out.println(Common.proUrlPrefix);
                System.out.println(Common.opensslPath);
                String strRes = VerifyIdentity.VerifyIdentity(username, pemPath);
                JSONObject jsonRes = JSONObject.parseObject(strRes);
                String code = jsonRes.getString("code");
                System.out.println(code);
                if (!"0".equals(code)) {
                    throw new BadRequestAlertException("身份校验失败！", "VerifyIdentity", "Authorization failure");
                }
            } catch (Exception e) {
                throw new BadRequestAlertException("身份校验失败！", "VerifyIdentity", "Unknown failure");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/jgraphAuthenticate")
    @Timed
    public ResponseEntity<Object> jgraphAuthorize(@Valid @RequestBody LoginVM loginVM, HttpServletRequest request) {

        System.out.println(loginVM.getUsername() +"  "+loginVM.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
