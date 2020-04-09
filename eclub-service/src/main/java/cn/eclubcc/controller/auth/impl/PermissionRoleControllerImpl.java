package cn.eclubcc.controller.auth.impl;

import cn.eclubcc.common.util.SecurityUtils;
import cn.eclubcc.controller.auth.PermissionRoleController;
import cn.eclubcc.pojo.auth.PermissionRole;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.PermissionRoleService;
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
@RequestMapping("/permissionRole")
@Slf4j
public class PermissionRoleControllerImpl implements PermissionRoleController {
  @Autowired private PermissionRoleService permissionRoleService;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_permissionRole_add')")
  public ResponseResult insertPermissionRole(@Validated PermissionRole permissionRole) {
    log.info("insert permissionRole is:{}", permissionRole);
    permissionRole.setOperator(SecurityUtils.getUserId());
    permissionRole.setId(null);
    permissionRole.setCreateTime(new Date());
    permissionRole.setUpdateTime(permissionRole.getCreateTime());
    return permissionRoleService.insertPermissionRole(permissionRole);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_permissionRole_delete')")
  public ResponseResult deletePermissionRole(@PathVariable String id) {
    return permissionRoleService.deletePermissionRole(id);
  }

  @Override
  @DeleteMapping
  @PreAuthorize("hasAuthority('eclub_admin_permissionRole_delete')")
  public ResponseResult deletePermissionRoleByIds(@RequestParam List<String> ids) {
    return permissionRoleService.deletePermissionRoleByIds(ids);
  }
}
