package cn.eclubcc.service.impl;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.AuthCodeEnum;
import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.auth.request.UserInfo;
import cn.eclubcc.pojo.auth.response.Jscode2SessionResponse;
import cn.eclubcc.pojo.auth.response.OpenIdResponse;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.AuthService;
import cn.eclubcc.service.UserService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Ikaros
 * @date 2020/3/31 13:27
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Value("${eclub.auth.appId}")
    private String appId;
    
    @Value("${eclub.auth.appSecret}")
    private String appSecret;
    
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    
    @Override
    public ResponseResult getOpenIdByCode(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appId, appSecret, code);
        ResponseEntity<Jscode2SessionResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, null, Jscode2SessionResponse.class);
        Jscode2SessionResponse jscode2SessionResponse = responseEntity.getBody();
        if (jscode2SessionResponse == null) {
            ExceptionCast.cast(AuthCodeEnum.GET_OPENID_FAIL);
        }
        /*
         * errcode:
         * -1 系统繁忙，此时请开发者稍候再试
         * 0 请求成功
         * 40029 code无效
         * 45011 频率限制，每个用户每分钟100次
         */
        String errcode = jscode2SessionResponse.getErrcode();
        // 请求出错
        if (Integer.valueOf(errcode) != 0) {
            ExceptionCast.cast(AuthCodeEnum.GET_OPENID_FAIL);
        }
        User user = new User();
        String openId = jscode2SessionResponse.getOpenid();
        String sessionKey = jscode2SessionResponse.getSession_key();
        user.setOpenId(openId);
        user.setSessionKey(sessionKey);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 此处主要是用于给setUserInfo方法使用
        request.getSession().setAttribute("user", user);
        log.info("openId is:{}", openId);
        log.info("sessionKey is:{}", sessionKey);
        return new OpenIdResponse(CommonCodeEnum.SUCCESS, openId);
    }
    
    
    @Override
    public ResponseResult setUserInfo(String userInfoStr, String signature) {
        // 签名验证
        String algrorithm = "SHA1";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getSession().getAttribute("user");
        String sessionKey = user.getSessionKey();
        if (StringUtils.isBlank(sessionKey)) {
            ExceptionCast.cast(AuthCodeEnum.SESSION_KEY_BLANK);
        }
        try {
            MessageDigest sha1 = MessageDigest.getInstance(algrorithm);
            byte[] digest = sha1.digest((userInfoStr + sessionKey).getBytes());
            if (!StringUtils.equals(Arrays.toString(digest), signature)) {
                ExceptionCast.cast(AuthCodeEnum.SIGNATURE_VALID_ERROR);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("获取{}加密算法出错", algrorithm, e);
        }
        // 签名验证成功
        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
        user.setNickname(userInfo.getNickName());
        user.setUserProfile(userInfo.getAvatarUrl());
        // 更新数据库信息
        userService.updateUserByOpenId(user);
        request.getSession().setAttribute("user", user);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
}
