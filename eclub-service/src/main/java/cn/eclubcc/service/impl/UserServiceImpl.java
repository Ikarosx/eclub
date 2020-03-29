package cn.eclubcc.service.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.dao.UserRepository;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ikaros
 * @date 2020/3/29 12:21
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Autowired UserRepository userRepository;

  @Override
  public ResponseResult insertUser(User user) {
    userRepository.save(user);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteUser(String id) {
    userRepository.deleteById(id);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteUserByIds(List<String> ids) {
    List<User> users =
        ids.stream()
            .map(
                id -> {
                  User user = new User();
                  user.setId(id);
                  return user;
                })
            .collect(Collectors.toList());
    userRepository.deleteAll(users);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public QueryResponseResult listUsersByPage(
      Integer page, Integer size, UserQueryParam userQueryParam) {
    User user = new User();
    BeanUtils.copyProperties(userQueryParam, user);
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase(true);
    Example<User> example = Example.of(user, matcher);
    // 排序 TODO
    List<Sort.Order> orders = new ArrayList<>();
    Sort sort = Sort.by(orders);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<User> pageUser = userRepository.findAll(example, pageable);
    QueryResult<User> queryResult = new QueryResult<>();
    queryResult.setList(pageUser.getContent());
    queryResult.setTotal(queryResult.getTotal());
    queryResult.setTotalPage(queryResult.getTotalPage());
    return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
  }
}
