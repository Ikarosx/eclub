package cn.eclubcc.pojo.auth;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/30 10:26
 */
@Data
@Entity
@Table
public class Permission {
  @Id
  @GeneratedValue(generator = "snowFlakeGenerator")
  private String id;

  @NotEmpty private String code;
  @NotEmpty private String parentId;
  @NotEmpty private String menuName;
  private String url;
  private Boolean menu;
  private Integer level;
  private Integer sort;
  private Integer state;
  private String iconUri;
  private Date createTime;
  private Date updateTime;
  private String operator;
}
