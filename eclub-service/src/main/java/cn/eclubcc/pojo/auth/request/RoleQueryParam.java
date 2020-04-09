package cn.eclubcc.pojo.auth.request;

import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/4/9 19:05
 */
@Data
public class RoleQueryParam {
    private String id;
    private String name;
    private String description;
    private Integer state;
}
