package cn.eclubcc.service;

import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.http.response.ResponseResult;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/28 18:28
 */
public interface ClubService {
  ResponseResult insertClub(Club club);

  ResponseResult deleteClubsByIds(List<String> ids);

  ResponseResult updateClub(Long id, Club club);
}
