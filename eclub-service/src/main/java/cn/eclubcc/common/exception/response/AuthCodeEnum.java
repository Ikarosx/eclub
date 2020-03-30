package cn.eclubcc.common.exception.response;

/**
 * @author Ikaros
 * @date 2020/3/30 15:56
 */
public enum AuthCodeEnum implements ResultCode {
    /** 认证代码20000 */
    ACCESS_DENIED_EXCEPTION(false, 20001, "没有权限"),
    SESSION_KEY_INVALID(false, 99998, "session_key过期");
    
    AuthCodeEnum(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    
    private boolean success;
    private int code;
    private String message;
    
    @Override
    public boolean success() {
        return success;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
