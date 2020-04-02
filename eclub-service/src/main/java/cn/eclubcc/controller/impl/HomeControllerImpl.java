package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.util.HomeCacheUtil;
import cn.eclubcc.controller.HomeController;
import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: markeNick
 */
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController {

    @Autowired
    private HomeService homeService;

    @Override
    @GetMapping("/clubList/{page}")
    public ResponseResult queryClubList(@PathVariable Integer page) throws InterruptedException{
        System.out.println("=====> queryClubList");
        List list = HomeCacheUtil.getCacheOfClubList(page);

        QueryResult<Object> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(list.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }

    @GetMapping("test")
    public ResponseResult test() {
        List list = homeService.queryClubListLimit(1, 10);

        QueryResult<Object> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(list.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}