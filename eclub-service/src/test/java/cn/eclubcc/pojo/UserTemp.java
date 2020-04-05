package cn.eclubcc.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/28 10:00
 */
@Data
@Entity
@Table(name = "user")
@GenericGenerator(name = "snowFlake", strategy = "cn.eclubcc.common.util.SnowFlakeIdGenerate")
public class UserTemp {
  @Id
  @GeneratedValue(generator = "snowFlake")
  String id;

  String username;
  String password;
}
