package cn.eclubcc.service;

import cn.eclubcc.pojo.activity.Activity;
import cn.eclubcc.pojo.activity.ActivityForDetail;

import java.util.List;


/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
public interface ActivityService {
    List<Activity> selectActivityDetailById(ActivityForDetail activity, Integer page, Integer limit, Integer state);
}
