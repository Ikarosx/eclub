package cn.eclubcc.controller;

import cn.eclubcc.pojo.activity.Activity;
import cn.eclubcc.pojo.activity.ActivityForDetail;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.*;

/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Api(value = "活动接口", tags = "活动接口")
public interface ActivityController {
    @ApiOperation(value = "查询活动详情页数据",
            notes = "通过可选参数获取活动详细数据" + "\n" + "可选参数:活动id、社团id、用户id" + "\n"
                    + "分页可选参数:页数page、每页记录数limit、活动状态state(0/1,默认是0)")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id", value = "活动id(可选)", type = "String"),
             @ApiImplicitParam(name = "clubId", value = "社团id(可选)", type = "String"),
             @ApiImplicitParam(name = "userId", value = "用户id(可选)", type = "String"),
             @ApiImplicitParam(name = "page", value = "页码(可选)", type = "Integer"),
             @ApiImplicitParam(name = "limit", value = "每页数量(可选)", type = "Integer"),
             @ApiImplicitParam(name = "state", value = "状态(可选0/1)", type = "Integer")}
    )
    ResponseResult getActivityDetail(ActivityForDetail activity, Integer page, Integer limit, Integer state) throws InterruptedException, IllegalAccessException, NoSuchFieldException;
}
