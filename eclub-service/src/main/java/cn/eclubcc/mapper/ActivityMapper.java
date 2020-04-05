package cn.eclubcc.mapper;

import cn.eclubcc.pojo.activity.Activity;
import cn.eclubcc.pojo.activity.ActivityForDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Mapper
public interface ActivityMapper {
    List<Activity> selectActivityDetailById(ActivityForDetail activity, Integer state);
}
    