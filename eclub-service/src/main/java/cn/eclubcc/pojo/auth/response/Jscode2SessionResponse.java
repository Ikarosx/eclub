package cn.eclubcc.pojo.auth.response;

import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/3/31 13:21
 */
@Data
public class Jscode2SessionResponse {
  private String openid;
  private String session_key;
  private String unionid;
  private String errcode;
  private String errmsg;
}
