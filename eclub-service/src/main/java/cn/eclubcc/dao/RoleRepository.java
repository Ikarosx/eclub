package cn.eclubcc.dao;

import cn.eclubcc.pojo.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/3/30 13:20
 */
public interface RoleRepository extends JpaRepository<Role, String> {}
