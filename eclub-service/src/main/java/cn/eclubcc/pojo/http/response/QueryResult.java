package cn.eclubcc.pojo.http.response;

import lombok.Data;

import java.util.List;

/**
 * 数据库查询之后封装到QueryResult
 * @author Ikaros
 * @date 2020/3/29 8:31
 */
@Data
public class QueryResult<T> {
  private List<T> list;
  private long total;
  private long totalPage;
}
