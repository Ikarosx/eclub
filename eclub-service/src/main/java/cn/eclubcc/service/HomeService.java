package cn.eclubcc.service;

import cn.eclubcc.pojo.Club;

import java.util.List;

public interface HomeService {

    List queryClubListLimit(Integer offset, Integer limit);
}
