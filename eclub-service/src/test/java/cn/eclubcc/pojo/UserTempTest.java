package cn.eclubcc.pojo;

import cn.eclubcc.common.util.Snowflake;
import cn.eclubcc.dao.UserTempRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/4/2 22:34
 */
// @SpringBootTest
// @RunWith(SpringRunner.class)
public class UserTempTest {
  @Autowired private UserTempRepository userTempRepository;

  @Test
  public void insertUserByJPATest() {
    long l1 = System.currentTimeMillis();
    UserTemp userTemp = new UserTemp();
    for (int i = 0; i < 10000; i++) {
      userTemp.setId(String.valueOf(Snowflake.nextId()));
      userTemp.setUsername("user" + i);
      userTemp.setPassword("123456");
      userTempRepository.save(userTemp);
    }
    long l2 = System.currentTimeMillis();
    System.out.println("JPA单条插入插入1w条耗时" + (l2 - l1) + "ms");
  }

  @Test
  public void insertUserByJPAListTest() {
    long l1 = System.currentTimeMillis();
    List<UserTemp> userTemps = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      UserTemp userTemp = new UserTemp();
      userTemp.setId(String.valueOf(Snowflake.nextId()));
      userTemp.setUsername("user" + i);
      userTemp.setPassword("123456");
      userTemps.add(userTemp);
    }
    userTempRepository.saveAll(userTemps);
    long l2 = System.currentTimeMillis();
    System.out.println("JPA列表插入插入1w条耗时" + (l2 - l1) + "ms");
  }

  @Test
  public void insertUserByBatchTest() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String jdbcUrl = "jdbc:mysql:///security?useSSL=false&rewriteBatchedStatements=true";
    Connection connection = DriverManager.getConnection(jdbcUrl, "security", "security");
    String insertSql =
        "INSERT INTO user (id, username, password, wx, qq, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
    long time = new java.util.Date().getTime();
    long l1 = System.currentTimeMillis();
    // 插入300w条，并ID不连续
    for (int i = 0; i < 3000000; i++) {
      // 使ID不连续
      Snowflake.nextId();
      preparedStatement.setString(1, String.valueOf(Snowflake.nextId()));
      preparedStatement.setString(2, "user" + i);
      preparedStatement.setString(3, "" + i);
      preparedStatement.setString(4, "wx" + i);
      preparedStatement.setString(5, "qq" + i);
      preparedStatement.setDate(6, new Date(time + i));
      preparedStatement.setDate(7, new Date(time + i));
      preparedStatement.addBatch();
    }

    // 1w条 209ms
    // 100w条 21466ms
    // 300w条 74415ms
    // 400w条 107027ms
    preparedStatement.executeBatch();
    long l2 = System.currentTimeMillis();
    System.out.println("Batch插入300w条耗时" + (l2 - l1) + "ms");
  }

  @Test
  public void selectUserTest() {}
}
