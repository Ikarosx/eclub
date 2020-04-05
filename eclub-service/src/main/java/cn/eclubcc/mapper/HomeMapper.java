package cn.eclubcc.mapper;

import cn.eclubcc.pojo.Club;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {

    /**
     * 根据类别查询社团列表
     * @param category
     * @return
     *
     * author:markeNick
     */
    List<Club> queryClubListLimit(@Param("category") String category);

    /**
     * 根据类别统计社团总数
     * @param category
     * @return
     *
     * author:markeNick
     */
    long countClubList(@Param("category") String category);
}