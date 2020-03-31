package cn.eclubcc.pojo.auth.request;

import cn.eclubcc.pojo.http.request.RequestData;
import lombok.Data;

/**
 * 小程序发送的用户数据
 *
 * @author Ikaros
 * @date 2020/3/31 14:09
 */
@Data
public class UserInfo implements RequestData {
  private String nickName;
  private Integer gender;
  private String language;
  private String city;
  private String province;
  private String country;
  private String avatarUrl;
}
