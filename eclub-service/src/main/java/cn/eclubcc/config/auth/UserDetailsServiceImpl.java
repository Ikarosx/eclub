package cn.eclubcc.config.auth;

import cn.eclubcc.pojo.UserExtension;
import cn.eclubcc.pojo.auth.AuthUser;
import cn.eclubcc.pojo.auth.Permission;
import cn.eclubcc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/16 9:15
 */
@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (StringUtils.isBlank(username)) {
      return null;
    }
    UserExtension userExtension = userService.getUserExtensionByUsername(username);
    if (userExtension == null) {
      return null;
    }
    String password = userExtension.getPassword();
    List<Permission> permissions = userExtension.getPermissions();
    if (permissions == null) {
      permissions = new ArrayList<>();
    }
    List<String> userPermission = new ArrayList<>();
    permissions.forEach(item -> userPermission.add(item.getCode()));
    String userPermissionString = StringUtils.join(userPermission.toArray(), ",");
    AuthUser authUser =
        new AuthUser(
            username,
            password,
            AuthorityUtils.commaSeparatedStringToAuthorityList(userPermissionString));
    authUser.setNickName(userExtension.getNickname());
    authUser.setStudentId(userExtension.getStudentId());
    authUser.setId(userExtension.getId());
    return authUser;
  }
}
