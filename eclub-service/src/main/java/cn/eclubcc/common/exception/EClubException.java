package cn.eclubcc.common.exception;

import cn.eclubcc.common.exception.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/3/28 15:46
 */
public class EClubException extends RuntimeException {
  private ResultCode resultCode;

  EClubException(ResultCode resultCode) {
    super("错误代码: " + resultCode.getCode() + " - 错误信息: " + resultCode.getMessage());
    this.resultCode = resultCode;
  }

  ResultCode getResultCode() {
    return resultCode;
  }
}
