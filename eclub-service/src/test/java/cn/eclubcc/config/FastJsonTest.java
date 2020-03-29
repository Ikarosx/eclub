package cn.eclubcc.config;

import cn.eclubcc.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/28 9:57
 */
//@AutoConfigureJsonTesters
//@RunWith(SpringRunner.class)
public class FastJsonTest {
//  private final Logger log = LoggerFactory.getLogger(FastJsonTest.class);
//
//  @Autowired private JacksonTester<User> json;
//
//  @Test
//  public void testSerialize() throws Exception {
//    User user = new User();
//    user.setUserName("Ikaros");
//    user.setId("1");
//    user.setCreateTime(new Date());
//    JsonContent<User> jsonContent = json.write(user);
//    log.info("json content: {}", jsonContent.getJson());
//  }
//
//  @Test
//  public void testDeserialize() throws Exception {
//    String content = "{\"userName\":\"Ikaros\",\"id\":1,\"createTime\":\"2019-01-01T10:47:30.222Z\"}";
//    User user = json.parseObject(content);
//    log.info("user is:" + user);
//  }
}
