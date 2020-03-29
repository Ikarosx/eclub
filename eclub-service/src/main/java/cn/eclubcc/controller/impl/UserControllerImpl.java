package cn.eclubcc.controller.impl;

import cn.eclubcc.controller.UserController;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
  @PostMapping
  public ResponseResult insertUser(@RequestBody @Validated User user) {
    user.setId(null);
    user.setCreateTime(new Date());
    user.setUpdateTime(user.getCreateTime());
    // 加密 TODO
    return userService.insertUser(user);
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
