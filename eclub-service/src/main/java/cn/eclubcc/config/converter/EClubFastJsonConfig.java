package cn.eclubcc.config.converter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/28 9:43
 */
@Configuration
public class EClubFastJsonConfig {
  @Bean
  public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    // 时间格式转换
    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    List<MediaType> fastMediaTypes = new ArrayList<>();
    // 中文乱码
    fastMediaTypes.add(MediaType.APPLICATION_JSON);
    fastMediaTypes.add(MediaType.TEXT_PLAIN);
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    fastJsonConfig.setCharset(Charset.forName("UTF-8"));
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    return fastJsonHttpMessageConverter;
  }

  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    return new HttpMessageConverters(fastJsonHttpMessageConverter());
  }

  /**
   * 用于发起请求
   *
   * @return RestTemplate
   */
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(fastJsonHttpMessageConverter());
    return restTemplate;
  }
}
