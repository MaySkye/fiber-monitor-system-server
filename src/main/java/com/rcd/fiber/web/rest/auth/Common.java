package com.rcd.fiber.web.rest.auth;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Configurable
public class Common {
    // 开发环境url
//    @Value("${tarobackend.devUrlPrefix}")
    public static String devUrlPrefix;
    // 正式环境url
//    @Value("${tarobackend.proUrlPrefix}")
    public static String proUrlPrefix = "https://192.168.253.2:4433/api/";

//    @Value("${tarobackend.opensslPath}")
    public static String opensslPath = "C:\\Program Files\\Git\\usr\\bin\\openssl";

    public static String getFileContent(FileInputStream fis, String encoding) throws IOException {
        try(BufferedReader br = new BufferedReader( new InputStreamReader(fis, encoding))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        }
    }
}
