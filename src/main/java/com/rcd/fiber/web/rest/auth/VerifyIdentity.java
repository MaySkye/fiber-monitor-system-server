package com.rcd.fiber.web.rest.auth;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.web.rest.UserJWTController;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PrivateKey;

public class VerifyIdentity {

    public static String VerifyIdentity(String username, String privateKeyPath) throws Exception {
        String url = Common.proUrlPrefix;
        String resp1;
        String resp2;
        System.out.println("====== 身份认证过程 ======");
        JSONObject jsonWrite = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonWrite.put("name", username);
        System.out.println("STEP 1: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp1 = HttpsPost.doPost(Common.proUrlPrefix + "user/verifyIdentity", jsonWrite.toJSONString(), "");
        } else {
            resp1 = HttpPost.doPost(Common.devUrlPrefix + "user/verifyIdentity", jsonWrite.toJSONString(), "");
        }
        System.out.println("RESP 1: " + resp1);

        JSONObject jsonRead = JSONObject.parseObject(resp1);
        Long code = (Long)jsonRead.get("code");
        System.out.println(code);

        PrivateKey privateKey = SignWithEC.getPrivateKey(privateKeyPath, Common.opensslPath);
        byte[] s = SignWithEC.sign(privateKey, String.valueOf(code));
        System.out.println("签名："+ Base64.encodeBase64String(s));

        jsonWrite.put("rand", code);
        jsonWrite.put("sign", Base64.encodeBase64String(s));
        jsonWrite.put("type", "user");
        System.out.println("STEP 2: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp2 = HttpsPost.doPost(Common.proUrlPrefix + "user/verifyIdentity", jsonWrite.toJSONString(), "");
        } else {
            resp2 = HttpPost.doPost(Common.devUrlPrefix + "user/verifyIdentity", jsonWrite.toJSONString(), "");
        }
        System.out.println("RESP 2: " + resp2);
        // 计算md5函数
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(s);
        String md5sum = new BigInteger(1, md.digest()).toString(16);
        System.out.println("MD5 Value: " + md5sum);
        UserJWTController.userMd5Map.put(username, md5sum);
        return resp2;
    }

    public static void main(String[] args) throws Exception{
        String username = "wangwei";
        String pemPath = "C:\\pairs\\wangwei\\wangwei.pem";
        VerifyIdentity(username, pemPath);
    }
}
