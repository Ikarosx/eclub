package cn.eclubcc.pojo.auth.request;

import cn.eclubcc.pojo.http.request.RequestData;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页查询用户参数类
 *
 * @author Ikaros
 * @date 2020/3/29 12:18
 */
@Data
public class UserQueryParam implements RequestData {
  private String studentId;
  private String username;
  private String nickname;
  private String phone;
  private String classGrade;
  private String wx;
  private String qq;
  private String openId;

  @Min(0)
  @Max(1)
  private Integer state;

  private String operator;
}
