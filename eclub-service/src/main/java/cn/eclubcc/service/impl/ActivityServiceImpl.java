package cn.eclubcc.service.impl;

import cn.eclubcc.mapper.ActivityMapper;
import cn.eclubcc.pojo.activity.Activity;
import cn.eclubcc.pojo.activity.ActivityForDetail;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.service.ActivityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Service("activityServiceImpl")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    @Override
    public List<Activity> selectActivityDetailById(ActivityForDetail activity, Integer page, Integer limit, Integer state) {
        PageHelper.startPage(page,limit,false);
        return activityMapper.selectActivityDetailById(activity, state);
    }
}
