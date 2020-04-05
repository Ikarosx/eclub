package cn.eclubcc.controller.impl;

import cn.eclubcc.common.util.SecurityUtils;
import cn.eclubcc.controller.UserController;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.UserService;
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
    user.setOperator(SecurityUtils.getUserId());
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
    // 检查是否可以为空
    return userService.deleteUser(id);
  }
  
  /**
   * 权限 TODO
   *
   * @param id
   * @param user
   * @return
   */
  @Override
  @PutMapping("/{id}")
  public ResponseResult updateUser(@PathVariable String id, @Validated User user) {
    user.setOperator(SecurityUtils.getUserId());
    String password = user.getPassword();
    if (StringUtils.isNotBlank(password)) {
      user.setPassword(passwordEncoder.encode(password));
    }
    user.setUpdateTime(new Date());
    return userService.updateUser(user);
  }

  @Override
  @DeleteMapping
  @PreAuthorize("hasAuthority('eclub_admin_user_delete')")
  public ResponseResult deleteUserByIds(List<String> ids) {
    return userService.deleteUserByIds(ids);
  }

  @Override
  @GetMapping("/list/{page}/{size}")
  @PreAuthorize("hasAuthority('eclub_admin_user_list')")
  public QueryResponseResult listUsersByPage(
      @PathVariable Integer page, @PathVariable Integer size, UserQueryParam userQueryParam) {
    if (page < 1) {
      page = 1;
    }
    if (size < 0) {
      size = 10;
    }
    return userService.listUsersByPage(page, size, userQueryParam);
  }
}
