package com.rcd.fiber.config.exceptionHandler;

public class PermissionException extends Exception {
    public static final String TYPE_TOKEN_FORMAT_ERROR = "Token格式不合法";
    public static final String TYPE_MATCH_USER_TYPE_ERROR = "角色类别错误";
    public static final String TYPE_MATCH_ROLE_ERROR = "无项目权限";
    public static final String TYPE_TOKEN_EXPIRED = "Token过期";
    public static final String TYPE_TOKEN_SIGNATURE_FAIL = "Token验证失败";
    public static final String TYPE_UNKNOWN = "未知异常";
    public static final String TYPE_AUTH_FAIL = "身份校验失败";


    public PermissionException(String msg, String cause) throws Exception {
        super(msg, new Throwable(cause));
    }
}
