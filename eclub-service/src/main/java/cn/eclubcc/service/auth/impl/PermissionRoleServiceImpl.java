package cn.eclubcc.service.auth.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.dao.PermissionRoleRepository;
import cn.eclubcc.pojo.auth.PermissionRole;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.PermissionRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ikaros
 * @date 2020/3/29 12:21
 */
@Service
@Slf4j
public class PermissionRoleServiceImpl implements PermissionRoleService {
  @Autowired private PermissionRoleRepository permissionRoleRepository;

  @Override
  public ResponseResult insertPermissionRole(PermissionRole permissionRole) {
    permissionRoleRepository.save(permissionRole);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deletePermissionRole(String id) {
    permissionRoleRepository.deleteById(id);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deletePermissionRoleByIds(List<String> ids) {
    List<PermissionRole> permissionRoles =
        ids.stream()
            .map(
                id -> {
                  PermissionRole permissionRole = new PermissionRole();
                  permissionRole.setId(id);
                  return permissionRole;
                })
            .collect(Collectors.toList());
    permissionRoleRepository.deleteAll(permissionRoles);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }
}
