package kr.go.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/properties/security/${spring.profiles.active}/security.properties")
public class PropertyConfig {

}
