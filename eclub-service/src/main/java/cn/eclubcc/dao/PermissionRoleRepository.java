package cn.eclubcc.dao;

import cn.eclubcc.pojo.auth.PermissionRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/04/09 19:36
 */
public interface PermissionRoleRepository extends JpaRepository<PermissionRole, String> {}
