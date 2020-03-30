package cn.eclubcc.dao;

import cn.eclubcc.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/3/29 12:14
 */
public interface UserRepository extends JpaRepository<User, String> {
  User findByUsername(String username);
    
    User findByOpenId(String openId);
}
