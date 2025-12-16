package kr.go.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
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

  // 3. Spring Security 설정
  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8", true);

    DelegatingFilterProxy securityFilter = new DelegatingFilterProxy();
    securityFilter.setTargetBeanName("springSecurityFilterChain");

    return new Filter[]{characterEncodingFilter, securityFilter};
  }

  // 4. Multipart 설정
  @Override
  protected void customizeRegistration(Dynamic registration) {
    // 파일 업로드 설정 (단위: 바이트)
    long maxFileSize = 10 * 1024 * 1024;       // 10 MB
    long maxRequestSize = 100 * 1024 * 1024;    // 100 MB
    int fileSizeThreshold = 2 * 1024 * 1024;    // 2 MB

    MultipartConfigElement multipartConfigElement =
        new MultipartConfigElement(null, maxFileSize, maxRequestSize, fileSizeThreshold);

    registration.setMultipartConfig(multipartConfigElement);
  }
  
}