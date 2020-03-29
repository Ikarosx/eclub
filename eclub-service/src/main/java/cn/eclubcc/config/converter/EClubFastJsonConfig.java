package cn.eclubcc.config.converter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author Ikaros
 * @date 2020/3/28 9:43
 */
@Configuration
public class EClubFastJsonConfig {
  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    // 时间格式转换
    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    fastJsonConfig.setCharset(Charset.forName("UTF-8"));
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    return new HttpMessageConverters(fastJsonHttpMessageConverter);
  }
}
