package cn.eclubcc.service.auth.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.dao.UserRoleRepository;
import cn.eclubcc.pojo.auth.UserRole;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.auth.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {
  @Autowired private UserRoleRepository userRoleRepository;

  @Override
  public ResponseResult insertUserRole(UserRole userRole) {
    userRoleRepository.save(userRole);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteUserRole(String id) {
    userRoleRepository.deleteById(id);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteUserRoleByIds(List<String> ids) {
    List<UserRole> userRoles =
        ids.stream()
            .map(
                id -> {
                  UserRole userRole = new UserRole();
                  userRole.setId(id);
                  return userRole;
                })
            .collect(Collectors.toList());
    userRoleRepository.deleteAll(userRoles);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }
}
