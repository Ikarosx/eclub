package cn.eclubcc.service.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.dao.ClubRepository;
import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/28 18:29
 */
@Service
public class ClubServiceImpl implements ClubService {
  @Autowired private ClubRepository clubRepository;

  @Override
  public ResponseResult insertClub(Club club) {
    clubRepository.save(club);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }

  @Override
  public ResponseResult deleteClubsByIds(List<String> ids) {
    return null;
  }

  @Override
  public ResponseResult updateClub(Long id, Club club) {
    return null;
  }
}
