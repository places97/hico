package kr.go.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket swaggerApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("00. ALL")
        .apiInfo(this.swaggerInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("kr.go.hico"))
        .paths(PathSelectors.any())
        .paths(regex("/api/.*"))
        .build()
        .useDefaultResponseMessages(false) // 기본 응답 메시지 (200, 401, 403, 404) 표시 여부
        //보안설정 추가
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()));

  }

  @Bean
  public Docket newsApiAccelerator() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("01. 관리자 API")
        .apiInfo(swaggerInfo())
        .select()
        .paths(regex("/adm.*"))
        .build()
        //보안설정 추가
        .securitySchemes(Collections.singletonList(apiKey()))
        .securityContexts(Collections.singletonList(securityContext()));
  }

  private ApiInfo swaggerInfo() {
    return new ApiInfoBuilder()
        .title("하이코칭 API")
        .description("API 목록")
        .version("1.0.0")
        .build();
  }

  // JWT 토큰 정의 (Bearer Token을 위한 ApiKey 설정)
  private SecurityScheme apiKey() {
    // 이름: "JWT" (임의의 이름)
    // 키 이름: "Authorization" (HTTP 헤더 이름)
    // 전달 위치: "header" (HTTP 헤더)
    return new ApiKey("AccessToken", "Authorization", "header");
  }

  // 보안 컨텍스트 정의 (어떤 API에 이 보안 설정을 적용할지 정의)
  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .forPaths(PathSelectors.any())
        //.forPaths(PathSelectors.regex("/api/.*"))
        .securityReferences(defaultAuth())
        .build();
  }

  // SecurityReference 정의 (JWT 정의를 API 호출에 적용)
  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(
        new SecurityReference("AccessToken", authorizationScopes));
  }
}