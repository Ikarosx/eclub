package cn.eclubcc.controller;

import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
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
  @ApiOperation(value = "添加用户")
  ResponseResult insertUser(User user);

  @ApiOperation(value = "通过ID删除用户")
  ResponseResult deleteUser(String id);

  @ApiOperation(value = "通过ID列表删除用户列表")
  ResponseResult deleteUserByIds(List<String> ids);

  @ApiOperation(value = "分页查询用户")
  ResponseResult listUsersByPage(Integer page, Integer size, UserQueryParam userQueryParam);
}
