package cn.eclubcc.controller.auth;

import cn.eclubcc.pojo.auth.PermissionRole;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 18:31
 */
@Api(value = "权限角色接口", tags = "权限角色接口")
public interface PermissionRoleController {
  @ApiOperation(value = "添加权限角色")
  ResponseResult insertPermissionRole(PermissionRole permissionRole);

  @ApiOperation(value = "通过ID删除权限角色")
  ResponseResult deletePermissionRole(String id);

  @ApiOperation(value = "通过ID列表删除权限角色列表")
  ResponseResult deletePermissionRoleByIds(List<String> ids);
}
