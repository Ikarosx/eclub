package cn.eclubcc.dao;

import cn.eclubcc.pojo.Club;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/3/28 18:29
 */
public interface ClubRepository extends JpaRepository<Club, String> {}
