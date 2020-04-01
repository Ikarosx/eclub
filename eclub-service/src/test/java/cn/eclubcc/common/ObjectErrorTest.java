package cn.eclubcc.common;

import org.junit.Test;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * @author Ikaros
 * @date 2020/4/1 18:06
 */
public class ObjectErrorTest {
  @Test
  public void objectErrorTest() {
    ObjectError fieldError = new FieldError("user", "createTime", "不能为空");
    System.out.println(fieldError instanceof FieldError);
  }
}
