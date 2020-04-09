package cn.eclubcc.controller.auth.impl;

import cn.eclubcc.common.util.SecurityUtils;
import cn.eclubcc.controller.auth.UserRoleController;
import cn.eclubcc.pojo.auth.UserRole;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/04/09 19:29
 */
@RestController
@RequestMapping("/userRole")
@Slf4j
public class UserRoleControllerImpl implements UserRoleController {
  @Autowired private UserRoleService userRoleService;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_userRole_add')")
  public ResponseResult insertUserRole(@Validated UserRole userRole) {
    log.info("insert userRole is:{}", userRole);
    userRole.setOperator(SecurityUtils.getUserId());
    userRole.setId(null);
    userRole.setCreateTime(new Date());
    userRole.setUpdateTime(userRole.getCreateTime());
    return userRoleService.insertUserRole(userRole);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_userRole_delete')")
  public ResponseResult deleteUserRole(@PathVariable String id) {
    return userRoleService.deleteUserRole(id);
  }

  @Override
  @DeleteMapping
  @PreAuthorize("hasAuthority('eclub_admin_userRole_delete')")
  public ResponseResult deleteUserRoleByIds(@RequestParam List<String> ids) {
    return userRoleService.deleteUserRoleByIds(ids);
  }
}
