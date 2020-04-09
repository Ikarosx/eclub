package cn.eclubcc.pojo.auth;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/30 10:26
 */
@Data
@Entity
@Table
public class PermissionRole {
  @Id
  @GeneratedValue(generator = "snowFlakeGenerator")
  private String id;

  @NotEmpty private String permissionId;
  @NotEmpty private String roleId;
  /** 0正常/1禁用 */
  @Min(0)
  @Max(1)
  @NotNull
  private Integer state;

  private Date createTime;
  private Date updateTime;
  private String operator;
}
