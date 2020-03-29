package cn.eclubcc.common.exception;

import cn.eclubcc.common.exception.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/03/28 16:25
 */
public class ExceptionCast {
  /** 使用此静态方法抛出自定义异常 */
  public static void cast(ResultCode resultCode) {
    throw new EClubException(resultCode);
  }
}
