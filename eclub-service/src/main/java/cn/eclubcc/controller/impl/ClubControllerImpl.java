package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.controller.ClubController;
import cn.eclubcc.pojo.Club;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.ClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author Ikaros
 * @date 2020/3/28 16:23
 */
@RestController
@RequestMapping("/club")
@Slf4j
public class ClubControllerImpl implements ClubController {
  @Autowired private ClubService clubService;

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('eclub_admin_club_add')")
  public ResponseResult insertClub(Club club) {
    // club.setId(null);
    // return clubService.insertClub(club);
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
