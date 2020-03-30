package cn.eclubcc.pojo.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Ikaros
 * @date 2020/3/30 11:00
 */
@Data
public class AuthUser extends User {
  private String id;

  private String studentId;
  private String userName;
  private String nickName;
  private String openId;

  public AuthUser(
      String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
}
