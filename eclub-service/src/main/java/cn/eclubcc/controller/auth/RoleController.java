package cn.eclubcc.controller.auth;

import cn.eclubcc.pojo.auth.Role;
import cn.eclubcc.pojo.auth.request.RoleQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 18:31
 */
@Api(value = "角色接口", tags = "角色接口")
public interface RoleController {
  @ApiOperation(value = "添加角色")
  ResponseResult insertRole(Role role);

  @ApiOperation(value = "通过角色ID删除角色")
  ResponseResult deleteRole(String id);

  @ApiOperation(value = "更新角色")
  ResponseResult updateRole(String id, Role role);

  @ApiOperation(value = "通过角色ID列表删除角色列表")
  ResponseResult deleteRoleByIds(List<String> ids);

  @ApiOperation(value = "分页查询角色")
  QueryResponseResult listRolesByPage(Integer page, Integer size, RoleQueryParam roleQueryParam);
}
