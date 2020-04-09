package cn.eclubcc.controller.auth.impl;

import cn.eclubcc.common.util.SecurityUtils;
import cn.eclubcc.controller.auth.RoleController;
import cn.eclubcc.pojo.auth.Role;
import cn.eclubcc.pojo.auth.request.RoleQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.RoleService;
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
@RequestMapping("/role")
@Slf4j
public class RoleControllerImpl implements RoleController {
  @Autowired private RoleService roleService;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_role_add')")
  public ResponseResult insertRole(@Validated Role role) {
    log.info("insert role is:{}", role);
    role.setOperator(SecurityUtils.getUserId());
    role.setId(null);
    role.setCreateTime(new Date());
    role.setUpdateTime(role.getCreateTime());
    return roleService.insertRole(role);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_role_delete')")
  public ResponseResult deleteRole(@PathVariable String id) {
    return roleService.deleteRole(id);
  }

  @Override
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_role_update')")
  public ResponseResult updateRole(@PathVariable String id, @Validated Role role) {
    role.setOperator(SecurityUtils.getUserId());
    role.setUpdateTime(new Date());
    return roleService.updateRole(role);
  }

  @Override
  @DeleteMapping
  @PreAuthorize("hasAuthority('eclub_admin_role_delete')")
  public ResponseResult deleteRoleByIds(@RequestParam List<String> ids) {
    return roleService.deleteRoleByIds(ids);
  }

  @Override
  @GetMapping("/list/{index}/{size}")
  @PreAuthorize("hasAuthority('eclub_admin_role_list')")
  public QueryResponseResult listRolesByPage(
      @PathVariable Integer index, @PathVariable Integer size, RoleQueryParam roleQueryParam) {
    if (index < 0) {
      index = 0;
    }
    if (size < 0) {
      size = 10;
    }
    int page = index / (size) + 1;
    return roleService.listRolesByPage(page, size, roleQueryParam);
  }
  
}
