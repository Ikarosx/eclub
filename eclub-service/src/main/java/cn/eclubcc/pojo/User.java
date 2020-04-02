package cn.eclubcc.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/29 11:47
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
  private static final long serialVersionUID = -6532182578272956566L;

  @Id
  @GeneratedValue(generator = "snowFlakeGenerator")
  private String id;

  private String studentId;

  @Pattern(regexp = "[0-9a-zA-Z]{3,32}", message = "用户名格式错误，请输入3-32位的数字、大小写字母")
  private String username;

  private String password;
  private String userProfile;

  @Pattern(regexp = "\\d{11}", message = "手机号码长度为11位")
  private String phone;

  private String classGrade;

  @Pattern(regexp = "[0-9a-zA-Z]{3,32}", message = "微信格式错误，请输入3-64位的数字、大小写字母")
  private String wx;

  @Pattern(regexp = "\\d{1,32}", message = "QQ号码长度为1-32位")
  private String qq;

  private String email;
  private String sessionKey;
  @NotEmpty private String nickname;
  @NotEmpty private String openId;
  /** 0正常/1禁用 */
  @Min(0)
  @Max(1)
  @NotNull
  private Integer state;

  private Date createTime;
  private Date updateTime;

  private String operator;
}
