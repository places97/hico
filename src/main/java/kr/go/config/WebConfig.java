package kr.go.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
    basePackages = {"kr.go.ctl"} // Controller
)

public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  // --- Thymeleaf Bean 정의 시작 ---

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

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    registry.addResourceHandler("/images/**").addResourceLocations("/images/");

    // Swagger 설정 시작
    // 💡 1. Swagger UI 정적 리소스 경로 (WebJars 표준 경로 사용)
    registry.addResourceHandler("/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");

    // 💡 2. swagger-ui.html 파일 자체에 대한 요청 처리 (가장 확실한 접근 방법)
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    // 💡 3. v3/api-docs 요청 처리 (API 정의 JSON)
    registry.addResourceHandler("/v3/api-docs/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
  }
}
// --- Thymeleaf Bean 정의 끝 ---


//public class WebConfig implements WebMvcConfigurer {
//
//  // 뷰 리졸버 설정 (JSP 파일 경로 지정)
//  @Override
//  public void configureViewResolvers(ViewResolverRegistry registry) {
//    registry.jsp("/WEB-INF/jsp/", ".jsp");
//  }
//
//  // 정적 리소스(CSS, JS, Image 등) 경로 설정
//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
//    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
//    registry.addResourceHandler("/images/**").addResourceLocations("/images/");
//  }
//
//}