package cn.eclubcc.pojo.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数验证报错信息
 *
 * @author Ikaros
 * @date 2020/4/1 18:12
 */
@Data
@AllArgsConstructor
public class ValidationMessage {
  private String field;
  @NotNull private String message;
}
