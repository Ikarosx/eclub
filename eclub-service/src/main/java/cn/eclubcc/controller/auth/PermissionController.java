package cn.eclubcc.controller.auth;

import cn.eclubcc.pojo.auth.Permission;
import cn.eclubcc.pojo.auth.request.PermissionQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 19:07
 */
@Api(value = "权限接口", tags = "权限接口")
public interface PermissionController {
  @ApiOperation(value = "添加权限")
  ResponseResult insertPermission(Permission permission);

  @ApiOperation(value = "通过权限ID删除权限")
  ResponseResult deletePermission(String id);

  @ApiOperation(value = "更新权限")
  ResponseResult updatePermission(String id, Permission permission);

  @ApiOperation(value = "通过权限ID列表删除权限列表")
  ResponseResult deletePermissionByIds(List<String> ids);

  @ApiOperation(value = "分页查询权限")
  QueryResponseResult listPermissionsByPage(
      Integer page, Integer size, PermissionQueryParam permissionQueryParam);
}
