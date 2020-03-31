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
import org.springframework.web.client.RestTemplate;
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
 * 此过滤器用于处理小程序和后台不同的登陆逻辑
 * 首先从session中获取user
 * 获取到放行
 * 获取不到说明没有认证
 * 判断是否是小程序的请求
 * 如果是小程序，说明是服务器session过期导致获取不到
 * 则从数据库拿出
 * @author Ikaros
 * @date 2020/3/30 14:49
 */
public class ApplicationFilter extends GenericFilterBean {
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user != null) {
            doFilter(httpServletRequest, httpServletResponse, chain);
        }
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        // 以openid 开头的Authorization请求头
        // 说明是小程序的请求,并且未认证
        if (StringUtils.isNotBlank(authorization) && StringUtils.startsWithIgnoreCase(authorization, "openid ")) {
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
