package cn.eclubcc.controller.auth.impl;

import cn.eclubcc.common.util.SecurityUtils;
import cn.eclubcc.controller.auth.PermissionController;
import cn.eclubcc.pojo.auth.Permission;
import cn.eclubcc.pojo.auth.request.PermissionQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.PermissionService;
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
@RequestMapping("/permission")
@Slf4j
public class PermissionControllerImpl implements PermissionController {
  @Autowired private PermissionService permissionService;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_permission_add')")
  public ResponseResult insertPermission(@Validated Permission permission) {
    log.info("insert permission is:{}", permission);
    permission.setOperator(SecurityUtils.getUserId());
    permission.setId(null);
    permission.setCreateTime(new Date());
    permission.setUpdateTime(permission.getCreateTime());
    return permissionService.insertPermission(permission);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_permission_delete')")
  public ResponseResult deletePermission(@PathVariable String id) {
    return permissionService.deletePermission(id);
  }

  @Override
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('eclub_admin_permission_update')")
  public ResponseResult updatePermission(@PathVariable String id, @Validated Permission permission) {
    permission.setOperator(SecurityUtils.getUserId());
    permission.setUpdateTime(new Date());
    return permissionService.updatePermission(permission);
  }

  @Override
  @DeleteMapping
  @PreAuthorize("hasAuthority('eclub_admin_permission_delete')")
  public ResponseResult deletePermissionByIds(@RequestParam List<String> ids) {
    return permissionService.deletePermissionByIds(ids);
  }

  @Override
  @GetMapping("/list/{index}/{size}")
  @PreAuthorize("hasAuthority('eclub_admin_permission_list')")
  public QueryResponseResult listPermissionsByPage(
      @PathVariable Integer index, @PathVariable Integer size, PermissionQueryParam permissionQueryParam) {
    if (index < 0) {
      index = 0;
    }
    if (size < 0) {
      size = 10;
    }
    int page = index / (size) + 1;
    return permissionService.listPermissionsByPage(page, size, permissionQueryParam);
  }
}
