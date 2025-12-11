package kr.go.config;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// web.xml을 대체하여 Spring MVC 설정을 초기화
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  // 1. Root Application Context 설정 (ContextLoaderListener 역할)
  // ContextConfig (애플리케이션 전반 설정)와 SecurityConfig (보안 설정)를 함께 반환합니다.
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{ContextConfig.class, SecurityConfig.class};
  }

  // 2. Servlet Context 설정 (DispatcherServlet의 init-param 역할)
  // dispatcher-servlet.xml (Controller, ViewResolver 등 Web 관련 설정) 설정
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[]{WebConfig.class};
  }

  // 3. DispatcherServlet 매핑 설정
  @Override
  protected String[] getServletMappings() {
    return new String[]{"/", "*.do"};
//    return new String[]{"*.do"};
  }

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8", true);

    DelegatingFilterProxy securityFilter = new DelegatingFilterProxy();
    securityFilter.setTargetBeanName("springSecurityFilterChain");

    return new Filter[]{characterEncodingFilter, securityFilter};
  }
}