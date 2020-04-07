package cn.eclubcc.pojo.http.response;

import cn.eclubcc.common.exception.response.ResultCode;
import lombok.Data;

/**
 * 用于返回查询接口的数据
 *
 * @author Ikaros
 * @date 2020/3/29 8:34
 */
@Data
public class QueryResponseResult extends ResponseResult {
  private QueryResult data;

  public QueryResponseResult(ResultCode resultCode, QueryResult data) {
    super(resultCode);
    this.data = data;
  }

  public QueryResponseResult(int code, boolean success, String message, QueryResult data) {
    super(code, success, message);
    this.data = data;
  }
}
