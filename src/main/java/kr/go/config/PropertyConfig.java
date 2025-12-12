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

    // 1. globals.properties 리소스
    Resource globalsResource = new ClassPathResource("properties/globals.properties");

    // 2. globals.properties를 먼저 로드하여 'active' 값을 읽음
    Properties globalsProps = PropertiesLoaderUtils.loadProperties(globalsResource);
    String activeProfile = globalsProps.getProperty("active");

    if (activeProfile == null || activeProfile.isEmpty()) {
      // 'active' 값이 없으면 예외 발생 또는 기본값 설정
      throw new IllegalStateException("globals.properties 파일에 'active' 값이 설정되어 있지 않습니다.");
    }

    // 3. 'active' 값에 따라 동적으로 프로파일별 properties 리소스 경로를 생성
    String profileLocation = "properties/globals-" + activeProfile + ".properties";
    Resource profileResource = new ClassPathResource(profileLocation);

    // 4. 두 리소스를 모두 등록하여 프로퍼티 사용 준비 완료
    configurer.setLocations(globalsResource, profileResource);

    // 해결할 수 없는 플레이스홀더를 무시하도록 설정 (선택 사항)
    configurer.setIgnoreUnresolvablePlaceholders(true);

    return configurer;
  }
}
