package cn.eclubcc.controller.auth.impl;

import cn.eclubcc.common.util.SecurityUtils;
import cn.eclubcc.controller.auth.UserController;
import cn.eclubcc.pojo.auth.User;
import cn.eclubcc.pojo.auth.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/29 13:22
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserControllerImpl implements UserController {
  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_user_add')")
  public ResponseResult insertUser(@Validated User user) {
    log.info("insert user is:{}", user);
    // user.setOperator(SecurityUtils.getUserId());
    user.setId(null);
    user.setCreateTime(new Date());
    user.setUpdateTime(user.getCreateTime());
    // 加密
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userService.insertUser(user);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_user_delete')")
  public ResponseResult deleteUser(@PathVariable String id) {
    return userService.deleteUser(id);
  }

  /**
   * 直接请求该方法需要权限
   *
   * @param user
   * @return
   */
  @Override
  @PutMapping
  @PreAuthorize("hasAuthority('eclub_admin_user_update')")
  public ResponseResult updateUserByAdmin(@Validated User user) {
    // 更新
    user.setOpenId(SecurityUtils.getUserId());
    String password = user.getPassword();
    if (StringUtils.isNotBlank(password)) {
      user.setPassword(passwordEncoder.encode(password));
    }
    user.setUpdateTime(new Date());
    return userService.updateUser(user);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseResult updateUser(@PathVariable String id, @Validated User user) {
    // ID设置为当前登录用户的ID
    user.setId(SecurityUtils.getUserId());
    return updateUserByAdmin(user);
  }

  @Override
  @DeleteMapping
  @PreAuthorize("hasAuthority('eclub_admin_user_delete')")
  public ResponseResult deleteUserByIds(@RequestParam List<String> ids) {
    return userService.deleteUserByIds(ids);
  }

  @Override
  @GetMapping("/list/{index}/{size}")
  @PreAuthorize("hasAuthority('eclub_admin_user_list')")
  public QueryResponseResult listUsersByPage(
      @PathVariable Integer index, @PathVariable Integer size, UserQueryParam userQueryParam) {
    if (index < 0) {
      index = 0;
    }
    if (size < 0) {
      size = 10;
    }
    int page = index / (size) + 1;
    return userService.listUsersByPage(page, size, userQueryParam);
  }
}
