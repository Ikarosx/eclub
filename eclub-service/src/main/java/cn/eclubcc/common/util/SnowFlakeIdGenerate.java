package cn.eclubcc.common.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author Ikaros
 * @date 2020/3/28 18:14
 */
public class SnowFlakeIdGenerate implements IdentifierGenerator {
  @Override
  public Serializable generate(
      SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
      throws HibernateException {
    return String.valueOf(Snowflake.nextId());
  }
}
