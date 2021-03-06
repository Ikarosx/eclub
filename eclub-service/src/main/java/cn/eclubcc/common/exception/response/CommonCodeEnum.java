package cn.eclubcc.common.exception.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/3/28 15:56
 */
@ToString
public enum CommonCodeEnum implements ResultCode {
  /** 通用代码10000 */
  SUCCESS(true, 10000, "操作成功"),
  INVALID_PARAM(false, 10001, "非法参数"),
  DATA_INTEGRITY_VIOLATION_EXCEPTION(false, 10002, "数据完整性约束异常"),
  HTTP_REQUEST_METHOD_NOT_SUPPORT_EDEXCEPTION(false, 10003, "HTTP请求方法不支持"),
  NOT_LOGIN(false, 99998, "用户未登录"),
  SERVER_ERROR(false, 99999, "服务器内部错误");
  
  CommonCodeEnum(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }

  private boolean success;
  private int code;
  private String message;

  @Override
  public boolean getSuccess() {
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
