package kr.go.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import kr.go.config.db.DBConfig;
import kr.go.config.db.ExtDBConfig;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:/egovProps/global.properties")
@ComponentScan(
    basePackages = {"kr.go.hico.*.*.svc", "kr.go.aspect", "kr.go.annotation", "kr.go.exception",
        "kr.go.util", "kr.go.config"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class)
    }
)
@Import({DBConfig.class, ExtDBConfig.class})
public class ContextConfig {

  // 💡 AccessDeniedHandler 빈을 루트 컨텍스트에 등록
  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, accessDeniedException) -> {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
      response.setContentType("application/json;charset=UTF-8");

      ResultVo resultVo = ResultVo.builder()
          .resCd("403")
          .resMsg("접근 권한이 없습니다.")
          .build();

      response.getWriter().write(new ObjectMapper().writeValueAsString(resultVo));
      response.getWriter().flush();
    };
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
      response.setContentType("application/json;charset=UTF-8");

      ResultVo resultVo = ResultVo.builder()
          .resCd("401")
          .resMsg("인증 정보가 없거나 유효하지 않습니다.")
          .build();

      response.getWriter().write(new ObjectMapper().writeValueAsString(resultVo));
      response.getWriter().flush();
    };
  }

}
