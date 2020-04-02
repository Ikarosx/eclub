package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.util.activity.ActivityCacheUntil;
import cn.eclubcc.controller.ActivityController;
import cn.eclubcc.pojo.activity.Activity;
import cn.eclubcc.pojo.activity.ActivityForDetail;
import cn.eclubcc.pojo.http.response.QueryResponseResult;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.ActivityService;
import com.github.pagehelper.PageHelper;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityControllerImpl implements ActivityController {
    //@NotNull:不能为null,但可以为empty (""," "," ")
    //@NotEmpty:不能为null,而且长度必须大于0 (" "," ")
    //@NotBlank:只能作用在String上,不能为null,而且调用trim()后,长度必须大于0
    @Autowired
    ActivityService activityService;

    /**
     *
     * @param activity(id(活动表id) 或 clubId 或 userId)
     * @param page
     * @param limit
     * @param state
     * @return
     */
    @GetMapping("/detail")
    @Override
    public ResponseResult getActivityDetail(ActivityForDetail activity,
                                            @RequestParam(defaultValue = "1")Integer page,
                                            @RequestParam(defaultValue = "10") Integer limit,
                                            @RequestParam(defaultValue = "0") Integer state) throws InterruptedException, IllegalAccessException, NoSuchFieldException {
        List<Activity> list = ActivityCacheUntil.getCacheOfActivityList(activity, activityService, page, limit, state);
        //TODO 还差一个数据 查询数量
        QueryResult<Activity> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(list.size());
        queryResult.setTotalPage(0);
        return new QueryResponseResult(CommonCodeEnum.SUCCESS,queryResult);
    }
}
