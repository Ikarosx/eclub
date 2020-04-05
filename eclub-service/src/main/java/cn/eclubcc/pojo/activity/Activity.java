package cn.eclubcc.pojo.activity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Data
@Table(name="activity")
public class Activity {
    @Id
    @GeneratedValue(generator = "snowFlakeGenerator")
    private String id;

    @NotEmpty
    private String title;

    @NotEmpty
    private Date time;
    @NotEmpty

    private String area;

    @NotEmpty
    private String description;

    @NotEmpty
    private String clubId;

    @NotEmpty
    private String userId;

    @NotEmpty
    private Integer isTop;

    @NotEmpty
    private Integer state;

    @NotEmpty
    private Date createTime;


    private Date updateTime;

    @NotEmpty
    private String operator;

}
