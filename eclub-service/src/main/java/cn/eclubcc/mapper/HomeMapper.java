package cn.eclubcc.mapper;

import cn.eclubcc.pojo.Club;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {

    List<Club> queryClubListLimit();
}