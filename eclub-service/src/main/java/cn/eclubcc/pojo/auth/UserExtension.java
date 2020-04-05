package cn.eclubcc.pojo.auth;

import lombok.Data;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/30 10:26
 */
@Data
public class UserExtension extends User {
    private List<Permission> permissions;
}
