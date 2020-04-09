package cn.eclubcc.pojo.auth.request;

import lombok.Data;

import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/30 10:26
 */
@Data
public class PermissionQueryParam {
  private String id;
  private String code;
  private String parentId;
  private String menuName;
  private Boolean menu;
  private Integer state;
}
