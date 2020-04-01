package cn.eclubcc.controller;

import cn.eclubcc.pojo.auth.response.OpenIdResponse;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/3/31 8:51
 */
@Api(value = "认证接口", tags = "认证接口")
public interface AuthController {
  @ApiOperation(
      value = "通过code得到openId",
      notes =
          "小程序授权之后应该携带code请求此接口，"
              + "在此接口中携带AppId、AppSecret和code访问微信服务器，"
              + "微信服务器返回session_key和openId，"
              + "接口返回openId和cookie给小程序")
  OpenIdResponse getOpenIdByCode(String code);

  @ApiOperation(
      value = "设置用户信息",
      notes = "用户通过getOpenIdByCode后得到了openid，然后在前端通过getUserInfo接口拿到用户数据，再带着数据和签名请求此接口进行用户信息的录入")
  ResponseResult setUserInfo(String userInfoStr, String signature);
}
