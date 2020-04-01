package cn.eclubcc.service;

import cn.eclubcc.pojo.auth.response.OpenIdResponse;
import cn.eclubcc.pojo.http.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/3/31 13:27
 */
public interface AuthService {
  OpenIdResponse getOpenIdByCode(String code);

  ResponseResult setUserInfo(String userInfoStr, String signature);
}
