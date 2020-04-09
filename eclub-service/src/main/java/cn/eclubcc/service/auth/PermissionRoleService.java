package cn.eclubcc.service.auth;

import cn.eclubcc.pojo.auth.PermissionRole;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/4/9 20:02
 */
public interface PermissionRoleService {
  ResponseResult insertPermissionRole(PermissionRole permissionRole);

  ResponseResult deletePermissionRole(String id);

  ResponseResult deletePermissionRoleByIds(List<String> ids);
}
