package cn.eclubcc.service.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.mapper.HomeMapper;
import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.User;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.service.HomeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;

    @Override
    public QueryResponseResult queryClubListLimit(Integer offset, Integer limit) {

        PageHelper.startPage(offset,limit);
        List<Object> list = homeMapper.queryClubListLimit();
        QueryResult<Object> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(list.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}