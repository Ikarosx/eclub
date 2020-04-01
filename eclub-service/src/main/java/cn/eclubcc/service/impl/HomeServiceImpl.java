package cn.eclubcc.service.impl;

import cn.eclubcc.mapper.HomeMapper;
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
    public List<Object> queryClubListLimit(Integer offset, Integer limit) {

        PageHelper.startPage(offset,limit);
        return homeMapper.queryClubListLimit();
    }
}