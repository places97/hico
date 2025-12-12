package kr.go.config;

import kr.go.config.db.DBConfig;
import kr.go.config.db.ExtDBConfig;
import kr.go.config.security.PropertyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
    basePackages = {"kr.go.hico.*.*.service", "kr.go.aspect", "kr.go.annotation", "kr.go.exception", "kr.go.util", "kr.go.config"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class)
    }
)
@Import({DBConfig.class, ExtDBConfig.class})
public class ContextConfig {

  // 💡 AccessDeniedHandler 빈을 루트 컨텍스트에 등록
  @Bean
  public static PropertyConfig egovPropertyConfig() {
    return new PropertyConfig();
  }
}
