package cn.eclubcc.pojo.http.response;

import cn.eclubcc.common.exception.response.ResultCode;
import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/3/28 16:05
 */
@Data
public class ResponseResult {
  int code;
  boolean success;
  String message;

  public ResponseResult(ResultCode resultCode) {
    // enum序列化有问题
    this.code = resultCode.getCode();
    this.success = resultCode.success();
    this.message = resultCode.getMessage();
  }
}
