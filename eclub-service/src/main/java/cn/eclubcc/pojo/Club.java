package cn.eclubcc.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/28 17:10
 */
@Data
@Entity
@Table(name = "club")
public class Club {
  @Id
  @GeneratedValue(generator = "snowFlakeGenerator")
  private String id;

  @Pattern(regexp = "[0-9a-zA-Z]{3,32}", message = "社团名称格式错误，请输入3-32位的数字、大小写字母")
  @NotEmpty
  private String name;

  @NotEmpty private String description;
  private String logoUri;
  private String regulationUri;

  /** 0正常1禁用 */
  @Min(0)
  @Max(1)
  private int state;

  @NotNull private Date createTime;
  @NotNull private Date updateTime;
  private String operator;

  private String type;
}
