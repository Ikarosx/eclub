package cn.eclubcc.controller.auth;

import cn.eclubcc.pojo.auth.UserRole;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 18:31
 */
@Api(value = "用户角色接口", tags = "用户角色接口")
public interface UserRoleController {
  @ApiOperation(value = "添加用户角色")
  ResponseResult insertUserRole(UserRole userRole);

  @ApiOperation(value = "通过ID删除用户角色")
  ResponseResult deleteUserRole(String id);

  @ApiOperation(value = "通过ID列表删除用户角色列表")
  ResponseResult deleteUserRoleByIds(List<String> ids);
}
