package cn.eclubcc.common.exception.response;

/**
 * @author Ikaros
 * @date 2020/4/1 17:22
 */
public enum UserCodeEnum implements ResultCode {
  /** 用户代码30100 */
  USER_NOT_EXIST(false, 30101, "用户不存在");

  UserCodeEnum(boolean success, int code, String message) {
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
