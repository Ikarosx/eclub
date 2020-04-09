package cn.eclubcc.service.auth.impl;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.exception.response.UserCodeEnum;
import cn.eclubcc.dao.PermissionRepository;
import cn.eclubcc.pojo.auth.Permission;
import cn.eclubcc.pojo.auth.request.PermissionQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ikaros
 * @date 2020/3/29 12:21
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
  @Autowired private PermissionRepository permissionRepository;

  @Override
  public ResponseResult insertPermission(Permission permission) {
    permissionRepository.save(permission);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult updatePermission(Permission permission) {
    Optional<Permission> optional = permissionRepository.findById(permission.getId());
    Permission newPermission = optional.orElse(null);
    if (newPermission == null) {
      ExceptionCast.cast(UserCodeEnum.ROLE_NOT_EXIST);
    }
    BeanUtils.copyProperties(permission, newPermission);
    permissionRepository.save(newPermission);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deletePermission(String id) {
    permissionRepository.deleteById(id);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deletePermissionByIds(List<String> ids) {
    List<Permission> permissions =
        ids.stream()
            .map(
                id -> {
                  Permission permission = new Permission();
                  permission.setId(id);
                  return permission;
                })
            .collect(Collectors.toList());
    permissionRepository.deleteAll(permissions);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public QueryResponseResult listPermissionsByPage(
      Integer page, Integer size, PermissionQueryParam permissionQueryParam) {
    Permission permission = new Permission();
    BeanUtils.copyProperties(permissionQueryParam, permission);
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase(true);
    Example<Permission> example = Example.of(permission, matcher);
    // 排序 TODO
    List<Sort.Order> orders = new ArrayList<>();
    Sort sort = Sort.by(orders);
    Pageable pageable = PageRequest.of(page - 1, size, sort);
    Page<Permission> pagePermission = permissionRepository.findAll(example, pageable);
    QueryResult<Permission> queryResult = new QueryResult<>();
    queryResult.setList(pagePermission.getContent());
    queryResult.setTotal(pagePermission.getTotalElements());
    queryResult.setTotalPage(pagePermission.getTotalPages());
    return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
  }
}
