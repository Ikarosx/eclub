package cn.eclubcc.service.auth;

import cn.eclubcc.pojo.auth.UserRole;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/4/9 20:02
 */
public interface UserRoleService {
  ResponseResult insertUserRole(UserRole userRole);

  ResponseResult deleteUserRole(String id);

  ResponseResult deleteUserRoleByIds(List<String> ids);
}
