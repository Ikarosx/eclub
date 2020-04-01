package cn.eclubcc.pojo.http.response;

import cn.eclubcc.common.exception.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 用于返回参数验证错误集合
 * @author Ikaros
 * @date 2020/4/1 18:13
 */
@Data
public class ValidationErrorResponse extends ResponseResult {
  private List<ValidationMessage> validationMessageList;

  public ValidationErrorResponse(
      ResultCode resultCode, List<ValidationMessage> validationMessageList) {
    super(resultCode);
    this.validationMessageList = validationMessageList;
  }
}
