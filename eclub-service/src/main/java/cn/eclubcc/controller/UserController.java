package cn.eclubcc.controller;

import cn.eclubcc.pojo.auth.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/29 13:20
 */
@Api(value = "用户接口", tags = "用户接口")
public interface UserController {
  @ApiOperation(value = "添加用户", notes = "管理员手动添加用户，用户密码为未加密，会在方法内进行加密")
  ResponseResult insertUser(User user);

  @ApiOperation(value = "通过用户ID删除用户")
  ResponseResult deleteUser(String id);

  @ApiOperation(value = "更新用户")
  ResponseResult updateUser(String id, User user);

  @ApiOperation(value = "通过用户ID列表删除用户列表")
  ResponseResult deleteUserByIds(List<String> ids);

  @ApiOperation(value = "分页查询用户")
  QueryResponseResult listUsersByPage(Integer page, Integer size, UserQueryParam userQueryParam);
}
