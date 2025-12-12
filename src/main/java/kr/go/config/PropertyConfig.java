package kr.go.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class PropertyConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

    // 1. JVM 시스템 속성에서 'active' 값을 먼저 확인합니다. (IntelliJ 설정값)
    String activeProfile = System.getProperty("globals.active"); // -> 여기서 "dev"가 로드되어야 함!

    // 2. 만약 시스템 속성에서 값이 없다면 (null이거나 비어있다면), globals.properties 파일을 로드합니다.
    if (activeProfile == null || activeProfile.isEmpty()) {
      Resource globalsResource = new ClassPathResource("properties/globals.properties");
      Properties globalsProps = PropertiesLoaderUtils.loadProperties(globalsResource);
      activeProfile = globalsProps.getProperty("active"); // -> 시스템 속성 없을 경우 'local'을 읽게 됨
    }

    if (activeProfile == null || activeProfile.isEmpty()) {
      // 'active' 값이 없으면 예외 발생 또는 기본값 설정
      throw new IllegalStateException("globals.properties 파일에 'active' 값이 설정되어 있지 않습니다.");
    }

    // 3. 'active' 값에 따라 동적으로 프로파일별 properties 리소스 경로를 생성
    String profileLocation = "properties/globals-" + activeProfile + ".properties";
    Resource profileResource = new ClassPathResource(profileLocation);

    // 4. 두 리소스를 모두 등록하여 프로퍼티 사용 준비 완료
    configurer.setLocations(profileResource);

    // 해결할 수 없는 플레이스홀더를 무시하도록 설정 (선택 사항)
    configurer.setIgnoreUnresolvablePlaceholders(true);

    return configurer;
  }
}
