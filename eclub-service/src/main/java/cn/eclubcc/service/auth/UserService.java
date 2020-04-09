package cn.eclubcc.service.auth;

import cn.eclubcc.pojo.auth.User;
import cn.eclubcc.pojo.auth.UserExtension;
import cn.eclubcc.pojo.auth.request.UserQueryParam;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/29 12:14
 */
public interface UserService {
  ResponseResult insertUser(User user);

  ResponseResult updateUser(User user);

  ResponseResult getUserByOpenId(String openId);

  ResponseResult deleteUser(String id);

  ResponseResult deleteUserByIds(List<String> ids);

  QueryResponseResult listUsersByPage(Integer page, Integer size, UserQueryParam userQueryParam);

  UserExtension getUserExtensionByUsername(String username);

  UserExtension getUserExtensionByOpenId(String openId);

  ResponseResult updateUserByOpenId(User user);
}
