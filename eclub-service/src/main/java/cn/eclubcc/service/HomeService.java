package cn.eclubcc.service;

import cn.eclubcc.pojo.http.response.QueryResponseResult;

import java.util.List;
import java.util.Map;

public interface HomeService {

    QueryResponseResult queryClubListLimit(Integer offset, Integer limit);
}
