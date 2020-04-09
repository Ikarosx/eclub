package cn.eclubcc.service.auth;

import cn.eclubcc.pojo.auth.Role;
import cn.eclubcc.pojo.auth.request.RoleQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 18:31
 */
public interface RoleService {
  ResponseResult insertRole(Role role);

  ResponseResult deleteRole(String id);

  ResponseResult updateRole(Role role);

  ResponseResult deleteRoleByIds(List<String> ids);

  QueryResponseResult listRolesByPage(Integer page, Integer size, RoleQueryParam roleQueryParam);
}
