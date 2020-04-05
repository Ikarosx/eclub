package cn.eclubcc.pojo.auth.response;

import cn.eclubcc.common.exception.response.ResultCode;
import cn.eclubcc.pojo.auth.User;
import cn.eclubcc.pojo.http.response.ResponseResult;
import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/3/31 15:04
 */
@Data
public class UserResponse extends ResponseResult {
    public UserResponse(ResultCode resultCode, User user) {
        super(resultCode);
        this.user = user;
    }
    
    private User user;
}
