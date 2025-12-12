package kr.go.config;

import java.util.List;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
    basePackages = {"kr.go.hico.*.*.web", "kr.go.config"}
)

public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

  private ApplicationContext applicationContext;

  private final ObjectMapper customObjectMapper;

  // 생성자를 통해 JacksonConfig에서 정의한 customObjectMapper 빈을 주입받습니다.
  public WebConfig(ObjectMapper customObjectMapper) {
    this.customObjectMapper = customObjectMapper;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  /**
   * 1. 템플릿 리졸버 (Template Resolver) 정의
   */
  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(this.applicationContext);
    templateResolver.setPrefix("/WEB-INF/views/"); // JSP 대신 HTML 파일을 여기에 배치
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(false); // 개발 중에는 false, 운영 시 true 권장
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

  /**
   * 2. 템플릿 엔진 (Template Engine) 정의
   */
  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true); // Spring Expression Language 활성화
    templateEngine.addDialect(new LayoutDialect());
    templateEngine.addDialect(new SpringSecurityDialect());

    return templateEngine;
  }

  /**
   * 3. Thymeleaf 뷰 리졸버 (View Resolver) 등록
   */
  @Bean
  public ThymeleafViewResolver viewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding("UTF-8");
    viewResolver.setOrder(1);
    return viewResolver;
  }

  /**
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    registry.addResourceHandler("/images/**").addResourceLocations("/images/");

    // Swagger
    registry.addResourceHandler("/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/v3/api-docs/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  /**
   * 5. 메세지 컨버터 커스터마이징
   *
   * @param converters initially an empty list of converters
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 기존의 Jackson 컨버터를 새 컨버터로 대체합니다.
    // Spring MVC는 기본적으로 MappingJackson2HttpMessageConverter를 사용합니다.
    MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

    // 주입받은 커스텀 ObjectMapper를 컨버터에 설정합니다.
    jacksonConverter.setObjectMapper(customObjectMapper);

    // 기존 컨버터 목록에 새 컨버터를 추가합니다. (가장 우선순위로 추가하는 것이 일반적)
    converters.add(jacksonConverter);

    // 만약 기본 컨버터를 완전히 대체하고 싶다면, converters.clear(); 후 추가하거나
    // 해당 컨버터를 리스트에서 제거하는 로직이 필요할 수 있습니다.
  }
}
