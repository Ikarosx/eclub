package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.controller.UserController;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参数校验/更新 TODO
 *
 * @author Ikaros
 * @date 2020/3/29 13:22
 */
@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
  @Autowired private UserService userService;

  @Override
  @GetMapping
  @PreAuthorize("hasAuthority('eclub_admin_user_add')")
  public ResponseResult insertUser(User user) {
    // user.setId(null);
    // user.setCreateTime(new Date());
    // user.setUpdateTime(user.getCreateTime());
    // 加密 TODO
    return new ResponseResult(CommonCodeEnum.SUCCESS);
    // return userService.insertUser(user);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseResult deleteUser(@PathVariable String id) {
    return userService.deleteUser(id);
  }

  @Override
  @DeleteMapping
  public ResponseResult deleteUserByIds(@RequestBody List<String> ids) {
    return userService.deleteUserByIds(ids);
  }

  @Override
  @GetMapping("/list/{page}/{size}")
  public ResponseResult listUsersByPage(
      @PathVariable Integer page,
      @PathVariable Integer size,
      @RequestParam UserQueryParam userQueryParam) {
    return userService.listUsersByPage(page, size, userQueryParam);
  }

}
