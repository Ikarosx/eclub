package cn.eclubcc.service.auth;

import cn.eclubcc.pojo.auth.Permission;
import cn.eclubcc.pojo.auth.request.PermissionQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 19:07
 */
public interface PermissionService {
  ResponseResult insertPermission(Permission permission);

  ResponseResult deletePermission(String id);

  ResponseResult updatePermission(Permission permission);

  ResponseResult deletePermissionByIds(List<String> ids);

  QueryResponseResult listPermissionsByPage(
      Integer page, Integer size, PermissionQueryParam permissionQueryParam);
}
