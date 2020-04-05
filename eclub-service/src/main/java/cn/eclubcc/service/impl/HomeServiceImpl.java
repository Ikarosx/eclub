package cn.eclubcc.service.impl;

import cn.eclubcc.mapper.HomeMapper;
import cn.eclubcc.pojo.Club;
import cn.eclubcc.service.HomeService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;

    @Override
    public List queryClubListLimit(Integer offset, Integer limit, String category) {
        PageHelper.startPage(offset, limit, false);
        return homeMapper.queryClubListLimit(category);
    }

    @Override
    public long queryClubCountByCategory(String category) {
        return homeMapper.countClubList(category);
    }


}