/** 包注解，雪花算法的主键生成策略 */
@GenericGenerator(
    name = "snowFlakeGenerator",
    strategy = "cn.eclubcc.common.util.SnowFlakeIdGenerate")
package cn.eclubcc.pojo;

import org.hibernate.annotations.GenericGenerator;
