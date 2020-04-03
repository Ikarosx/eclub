package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.util.HomeCacheUtil;
import cn.eclubcc.controller.HomeController;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = {"/clubList/{current}/{limit}", "/clubList/{current}/{limit}/{category}"}, method = RequestMethod.GET)
    public ResponseResult queryClubList(@PathVariable @Value("0") Integer current,
                                        @PathVariable @Value("10") Integer limit,
                                        @PathVariable @Value("0") String category) throws InterruptedException{

        double temp = current / limit;
//        int page = (int)(temp % 10 > 0 ? (int) temp + 1 : temp);
        int page = (int) Math.ceil(temp);
        Map<String, Object> map = HomeCacheUtil.getCacheOfClubList(page, limit, category, homeService);
        List list = (List) map.get("list");

        QueryResult<Object> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(list.size());
        queryResult.setTotalPage((long) map.get("count"));
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }

}