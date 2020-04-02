package cn.eclubcc.pojo.activity;

import lombok.Data;

/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Data
public class ActivityForDetail {
    //用于接收查询活动详情接口参数的pojo
    private String id;
    private String clubId;
    private String userId;
}
