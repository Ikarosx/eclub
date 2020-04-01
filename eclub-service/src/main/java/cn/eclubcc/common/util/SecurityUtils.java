package cn.eclubcc.common.util;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.pojo.auth.AuthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Ikaros
 * @date 2020/4/1 20:39
 */
public class SecurityUtils {
  private SecurityUtils() {}

  public static String getUserId() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String userId = null;
    if (authentication != null) {
      AuthUser userDetails = (AuthUser) authentication.getPrincipal();
      userId = userDetails.getId();
    }
    if (StringUtils.isBlank(userId)) {
      ExceptionCast.cast(CommonCodeEnum.NOT_LOGIN);
    }
    return userId;
  }
}
