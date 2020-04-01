package cn.eclubcc.controller;

import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "首页接口", tags = "首页接口")
public interface HomeController {

    @ApiOperation(value = "首页数据")
    ResponseResult home(Integer page);
}
