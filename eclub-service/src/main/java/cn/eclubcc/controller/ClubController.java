package cn.eclubcc.controller;

import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/28 15:43
 */
@Api(value = "社团接口", tags = "社团接口")
public interface ClubController {
  
  @ApiOperation(value = "添加社团")
  ResponseResult insertClub(Club club);

  @ApiOperation(value = "通过ID批量删除社团")
  ResponseResult deleteClubsByIds(List<String> ids);

  @ApiOperation(value = "更新社团")
  ResponseResult updateClub(Long id, Club club);
}
