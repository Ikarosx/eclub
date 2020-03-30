package cn.eclubcc.dao;

import cn.eclubcc.pojo.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/30 13:20
 */
public interface PermissionRepository extends JpaRepository<Permission, String> {

  @Query(
      nativeQuery = true,
      value =
          " SELECT p.id, p.code, p.menu_name, p.parent_id, p.is_menu menu, p.level, p.sort, p.icon_uri, p.url, p.state, p.create_time, p.update_time, p.operator FROM user u LEFT JOIN user_role u_r ON u.id = u_r.user_id LEFT JOIN permission_role p_r ON u_r.role_id = p_r.role_id LEFT JOIN permission p ON p_r.permission_id = p.id")
  List<Permission> listPermissions(String userId);
}
