package cn.eclubcc.service;

import java.util.List;

public interface HomeService {

    List queryClubListLimit(Integer offset, Integer limit, String category);
}
