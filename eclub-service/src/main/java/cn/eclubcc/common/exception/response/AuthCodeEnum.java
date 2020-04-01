package cn.eclubcc.common.exception.response;

/**
 * @author Ikaros
 * @date 2020/3/30 15:56
 */
public enum AuthCodeEnum implements ResultCode {
  /** 认证代码20000 */
  ACCESS_DENIED_EXCEPTION(false, 20001, "没有权限"),
  GET_OPENID_OBJECT_EMPTY(false, 20002, "jscode2session对象为空"),
  WX_SYSTEM_ERROR(false, 20003, "微信系统繁忙，请稍后再试"),
  CODE_INVALID(false, 20004, "code无效"),
  FREQUENCY_LIMIT(false, 20005, "频率限制，每个用户每分钟100次"),
  CODE_IS_BLANK(false, 20006, "收到的code为空"),
  SIGNATURE_VALID_ERROR(false, 20007, "签名验证失败"),
  SERVER_SESSION_KEY_BLANK(false, 20008, "服务器sessionKey为空"),
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
