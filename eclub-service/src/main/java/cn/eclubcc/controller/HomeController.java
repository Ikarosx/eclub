package cn.eclubcc.controller;

import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "首页接口", tags = "首页接口")
public interface HomeController {

    @ApiOperation(value = "社团列表数据")
    ResponseResult queryClubList(Integer page, Integer limit) throws InterruptedException;
}
