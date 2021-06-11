package com.rcd.fiber.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;

/**
 * View Model object for storing a user's credentials.
 */
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = ManagedUserVM.PASSWORD_MIN_LENGTH, max = ManagedUserVM.PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

    private String pemFileContent;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getPemFileContent() {
        return pemFileContent;
    }

    public void setPemFileContent(String pemFileContent) {
        this.pemFileContent = pemFileContent;
    }

    public static String savePemFile(String pemFileContent, String username) throws Exception
    {
        if(pemFileContent==null || pemFileContent.length() == 0) return null;
        String filePath = MessageFormat.format("{0}/{1}-{2}.pem", System.getProperty("java.io.tmpdir"), username, System.currentTimeMillis());
        File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(pemFileContent.getBytes());
            fileOutputStream.close();
        return filePath;
    }

    @Override
    public String toString() {
        return "LoginVM{" +
            "username='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
