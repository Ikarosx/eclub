package cn.eclubcc.aspect;

import cn.eclubcc.common.util.RedisUtil;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Ikaros
 * @date 2020/4/7 16:40
 */
@Aspect
@Component
@Slf4j
public class ListByPageAspect {

  @Pointcut("execution(* cn.eclubcc.controller.*.list*ByPage(..)))")
  public void listByPageAspect() {}

  /**
   * 拦截ByPage结尾的方法，即查询方法 先从redis获取取不到从数据库查，再将值放入redis中 redis key：domain:list:md5(uri) uri由访问路径与参数组成
   * redis value：QueryResponseResult形式的返回值
   *
   * @param joinPoint
   * @return
   */
  @Around("listByPageAspect()")
  public Object around(ProceedingJoinPoint joinPoint) {
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    Object[] args = joinPoint.getArgs();
    String requestURI = request.getRequestURI();
    String domain = StringUtils.substringBefore(requestURI, "/list");
    // 第三个参数固定为params
    String uriWithParam = requestURI + "?" + args[2].toString();
    // domain:list:md5(uri)
    String key = domain + ":" + "list:" + DigestUtils.md5DigestAsHex(uriWithParam.getBytes());
    String value = (String) RedisUtil.get(key);
    Object result = null;
    if (StringUtils.isNotBlank(value)) {
      // JSON.parseObject enum导致失败
      // ResultCode enum序列化失败，采取直接用参数生成
      Map map = (Map) JSON.parse(value);
      int code = (int) map.get("code");
      boolean success = (boolean) map.get("success");
      String message = (String) map.get("message");
      // queryResult
      QueryResult data = new QueryResult();
      Map dataMap = (Map) map.get("data");
      data.setTotalPage((int) dataMap.get("totalPage"));
      data.setTotal((int) dataMap.get("total"));
      data.setList((List) dataMap.get("list"));
      return new QueryResponseResult(code, success, message, data);
    }
    try {
      result = joinPoint.proceed(args);
      // 存入redis
      RedisUtil.set(key, JSON.toJSONString(result), 10);
    } catch (Throwable throwable) {
      log.error("listByPageAspect exception", throwable);
    }
    return result;
  }
}
