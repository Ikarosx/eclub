package cn.eclubcc.service.auth.impl;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.exception.response.UserCodeEnum;
import cn.eclubcc.dao.RoleRepository;
import cn.eclubcc.pojo.auth.Role;
import cn.eclubcc.pojo.auth.request.RoleQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.RoleService;
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
public class RoleServiceImpl implements RoleService {
  @Autowired private RoleRepository roleRepository;

  @Override
  public ResponseResult insertRole(Role role) {
    roleRepository.save(role);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult updateRole(Role role) {
    Optional<Role> optional = roleRepository.findById(role.getId());
    Role newRole = optional.orElse(null);
    if (newRole == null) {
      ExceptionCast.cast(UserCodeEnum.ROLE_NOT_EXIST);
    }
    BeanUtils.copyProperties(role, newRole);
    roleRepository.save(newRole);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteRole(String id) {
    roleRepository.deleteById(id);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteRoleByIds(List<String> ids) {
    List<Role> roles =
        ids.stream()
            .map(
                id -> {
                  Role role = new Role();
                  role.setId(id);
                  return role;
                })
            .collect(Collectors.toList());
    roleRepository.deleteAll(roles);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public QueryResponseResult listRolesByPage(
      Integer page, Integer size, RoleQueryParam roleQueryParam) {
    Role role = new Role();
    BeanUtils.copyProperties(roleQueryParam, role);
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase(true);
    Example<Role> example = Example.of(role, matcher);
    // 排序 TODO
    List<Sort.Order> orders = new ArrayList<>();
    Sort sort = Sort.by(orders);
    Pageable pageable = PageRequest.of(page - 1, size, sort);
    Page<Role> pageRole = roleRepository.findAll(example, pageable);
    QueryResult<Role> queryResult = new QueryResult<>();
    queryResult.setList(pageRole.getContent());
    queryResult.setTotal(pageRole.getTotalElements());
    queryResult.setTotalPage(pageRole.getTotalPages());
    return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
  }
}
