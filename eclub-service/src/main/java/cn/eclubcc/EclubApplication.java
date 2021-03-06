package cn.eclubcc;

import lombok.extern.java.Log;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ikaros
 * @date 2020/3/28 9:39
 */
@SpringBootApplication
@Log
@MapperScan("cn.eclubcc.mapper")
public class EclubApplication {
  public static void main(String[] args) {
    try {
      SpringApplication.run(EclubApplication.class, args);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("容器启动错误=>" + e.getMessage());
    }
  }
}
