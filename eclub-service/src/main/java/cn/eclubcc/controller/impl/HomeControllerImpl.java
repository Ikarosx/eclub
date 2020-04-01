package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.util.HomeCacheUtil;
import cn.eclubcc.controller.HomeController;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: markeNick
 */
@RestController
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController {

    @Autowired
    private HomeService homeService;

    @Override
    @GetMapping("/clubList/{page}")
    public ResponseResult home(@PathVariable Integer page) throws InterruptedException{
        
        List<Object> list = HomeCacheUtil.getCacheOfClubList(page);

        QueryResult<Object> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(list.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}