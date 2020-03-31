package cn.eclubcc.controller.impl;

import cn.eclubcc.controller.AuthController;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author Ikaros
 * @date 2020/3/31 13:02
 */
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * 小程序授权之后应该携带code和加密数据请求此接口
     * 在此接口中携带AppId、AppSecret和code访问微信服务器
     * 微信服务器返回session_key和openId
     * 接口返回openId和cookie给小程序
     *
     * @param code
     *
     * @return
     */
    @Override
    @PostMapping("/openid")
    public ResponseResult getOpenIdByCode(@NotNull String code) {
        return authService.getOpenIdByCode(code);
    }
    
    /**
     * 用户通过getOpenIdByCode后得到了openid
     * 然后在前端通过getUserInfo接口拿到用户数据
     * 再带着用户数据和签名请求此接口进行用户信息的录入
     *
     * @param userInfoStr
     *         用户信息json串
     * @param signature
     *         签名
     *
     * @return ResponseResult
     */
    @Override
    @PutMapping("/userinfo")
    public ResponseResult setUserInfo(String userInfoStr, String signature) {
        return authService.setUserInfo(userInfoStr, signature);
    }
}
