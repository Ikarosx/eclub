package cn.eclubcc.config;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.pojo.auth.User;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ikaros
 * @date 2020/3/28 9:57
 */
// @AutoConfigureJsonTesters
// @RunWith(SpringRunner.class)
public class FastJsonTest {
  @Test
  public void jsonTest() {
    QueryResult<User> queryResult = new QueryResult<>();
    ArrayList<User> list = new ArrayList<>();
    User user = new User();
    user.setId("111");
    list.add(user);
    queryResult.setList(list);
    QueryResponseResult queryResponseResult1 =
        new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    String value =
        "{\"code\":10000,\"data\":{\"list\":[{}],\"total\":0,\"totalPage\":0},\"message\":\"操作成功\",\"success\":true}";
    Map map = (Map) JSON.parse(value);
    QueryResult queryResult1 = new QueryResult<>();
    Map data = (Map) map.get("data");
    queryResult1.setTotalPage((int) data.get("totalPage"));
    queryResult1.setTotal((int) data.get("total"));
    queryResult1.setList((List) data.get("list"));
    System.out.println(queryResult1);
    // ResponseResult responseResult =
    //     new ResponseResult(
    //         (int) map.get("code"), (boolean) map.get("success"), (String) map.get("message"));
    // new QueryResponseResult()
    // System.out.println(JSON.parseObject(x, features, SerializerFeature.EMPTY));

    // String value =
    //
    // "{\"code\":10000,\"data\":{\"list\":[{}],\"total\":0,\"totalPage\":0},\"message\":\"操作成功\",\"getSuccess\":true}";
    // QueryResponseResult queryResponseResult = JSON.parseObject(value, QueryResponseResult.class);
    // System.out.println(queryResponseResult);
  }

  //  private final Logger log = LoggerFactory.getLogger(FastJsonTest.class);
  //
  //  @Autowired private JacksonTester<UserTemp> json;
  //
  //  @Test
  //  public void testSerialize() throws Exception {
  //    UserTemp user = new UserTemp();
  //    user.setUserName("Ikaros");
  //    user.setId("1");
  //    user.setCreateTime(new Date());
  //    JsonContent<UserTemp> jsonContent = json.write(user);
  //    log.info("json content: {}", jsonContent.getJson());
  //  }
  //
  //  @Test
  //  public void testDeserialize() throws Exception {
  //    String content =
  // "{\"userName\":\"Ikaros\",\"id\":1,\"createTime\":\"2019-01-01T10:47:30.222Z\"}";
  //    UserTemp user = json.parseObject(content);
  //    log.info("user is:" + user);
  //  }
}
