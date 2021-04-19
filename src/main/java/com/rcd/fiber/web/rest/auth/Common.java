package com.rcd.fiber.web.rest.auth;

import com.rcd.fiber.utils.WWLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Common {
    // 开发环境url
    public static final String devUrlPrefix = "http://localhost:9090/";
    // 正式环境url
    public static String proUrlPrefix = "https://10.109.253.3:20185/api/";
    public static String opensslPath = "C:\\Program Files\\Git\\usr\\bin\\openssl";

    @Value("${auth.url}")
    public void setProUrlPrefix(String proUrlPrefix) {
        Common.proUrlPrefix = proUrlPrefix;
    }


    @Value("${auth.opensslPath}")
    public void setOpensslPath(String opensslPath) {
        Common.opensslPath = opensslPath;
    }

    @PostConstruct
    public void logStatus(){
        WWLogger.info("授权管理： proUrlPrefix：" + proUrlPrefix + "，opensslPath" + opensslPath);
    }


    public static String getFileContent(FileInputStream fis, String encoding) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        }
    }
}
