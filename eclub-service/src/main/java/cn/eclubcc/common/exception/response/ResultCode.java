package cn.eclubcc.common.exception.response;

/**
 * 通用代码10000
 * 认证代码20000
 * 用户、角色、权限代码30100
 * 文件代码30200
 * 重新登录99998
 * @author Ikaros
 * @date 2020/3/28 15:49
 */
public interface ResultCode {
  boolean getSuccess();

  int getCode();

  String getMessage();
}
