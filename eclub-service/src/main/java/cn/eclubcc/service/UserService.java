package cn.eclubcc.service;

import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/29 12:14
 */
public interface UserService {
  ResponseResult insertUser(User user);

  ResponseResult deleteUser(String id);

  ResponseResult deleteUserByIds(List<String> ids);

  ResponseResult listUsersByPage(Integer page, Integer size, UserQueryParam userQueryParam);
}
