package cn.eclubcc.service;

import cn.eclubcc.pojo.auth.request.UserInfo;
import cn.eclubcc.pojo.http.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/3/31 13:27
 */
public interface AuthService {
  ResponseResult getOpenIdByCode(String code);

  ResponseResult setUserInfo(String userInfoStr, String signature);
}
