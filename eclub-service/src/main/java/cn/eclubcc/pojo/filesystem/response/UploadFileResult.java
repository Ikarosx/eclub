package cn.eclubcc.pojo.filesystem.response;

import cn.eclubcc.common.exception.response.ResultCode;
import cn.eclubcc.pojo.http.response.ResponseResult;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/04/05 09:43
 */
@Data
@ToString
public class UploadFileResult extends ResponseResult {
  String url;

  public UploadFileResult(ResultCode resultCode, String url) {
    super(resultCode);
    this.url = url;
  }
}
