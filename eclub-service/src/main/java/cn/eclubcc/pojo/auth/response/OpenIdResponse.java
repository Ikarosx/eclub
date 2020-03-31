package cn.eclubcc.pojo.auth.response;

import cn.eclubcc.common.exception.response.ResultCode;
import cn.eclubcc.pojo.http.response.ResponseResult;
import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/3/31 13:42
 */
@Data
public class OpenIdResponse extends ResponseResult {
  private String openId;

  public OpenIdResponse(ResultCode resultCode, String openId) {
    super(resultCode);
    this.openId = openId;
  }
}
