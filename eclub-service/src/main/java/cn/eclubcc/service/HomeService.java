package cn.eclubcc.service;

import java.util.List;

public interface HomeService {

    List<Object> queryClubListLimit(Integer offset, Integer limit);
}
