package cn.eclubcc.filter;

import cn.eclubcc.common.exception.response.AuthCodeEnum;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.UserExtension;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ikaros
 * @date 2020/3/30 14:49
 */
public class ApplicationFilter extends GenericFilterBean {
  @Autowired UserService userService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    User user = (User) httpServletRequest.getSession().getAttribute("user");
    if (user != null) {
      doFilter(httpServletRequest, httpServletResponse, chain);
    }
    String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    // 以openid 开头的Authorization请求头
    // 说明是小程序的请求,并且未认证
    if (StringUtils.isNotBlank(authorization)
        && StringUtils.startsWithIgnoreCase(authorization, "openid ")) {
      // 判断session_key是否有效
      String openId = StringUtils.substringAfter(authorization, "openid ");
      UserExtension userExtension = userService.getUserExtensionByOpenId(openId);
      String sessionKey = userExtension.getSessionKey();
      boolean sessionKeyValid = validateSessionKey(sessionKey);
      // session_key失效
      if (!sessionKeyValid) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = httpServletResponse.getWriter();
        ResponseResult responseResult = new ResponseResult(AuthCodeEnum.SESSION_KEY_INVALID);
        writer.write(JSON.toJSONString(responseResult));
        return;
      }
      // session_key有效
      // 将对象存入session
      httpServletRequest.getSession().setAttribute("user", userExtension);
      // 构建新的Authorization用于通过验证

    }
  }

  private boolean validateSessionKey(String sessionKey) {
    // TODO
    return true;
  }
}
