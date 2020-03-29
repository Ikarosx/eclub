package cn.eclubcc.common.exception.response;

/**
 * 通用代码10000
 *
 * @author Ikaros
 * @date 2020/3/28 15:49
 */
public interface ResultCode {
  boolean success();

  int getCode();

  String getMessage();
}
