package cn.eclubcc.controller.impl;

import cn.eclubcc.controller.UserController;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_user_add')")
  public ResponseResult insertUser(@Validated User user) {
    // user.setOperator(auth.getId());
    user.setId(null);
    user.setCreateTime(new Date());
    user.setUpdateTime(user.getCreateTime());
    // 加密
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userService.insertUser(user);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseResult deleteUser(@PathVariable String id) {
    // 检查是否可以为空
    return userService.deleteUser(id);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseResult updateUser(@PathVariable String id, @Validated User user) {
    String password = user.getPassword();
    if (StringUtils.isNotBlank(password)) {
      user.setPassword(passwordEncoder.encode(password));
    }
    user.setUpdateTime(new Date());
    return userService.updateUser(user);
  }

  @Override
  @DeleteMapping
  public ResponseResult deleteUserByIds(List<String> ids) {
    return userService.deleteUserByIds(ids);
  }

  @Override
  @GetMapping("/list/{page}/{size}")
  public QueryResponseResult listUsersByPage(
      @PathVariable Integer page, @PathVariable Integer size, UserQueryParam userQueryParam) {
    return userService.listUsersByPage(page, size, userQueryParam);
  }
}
