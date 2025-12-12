package kr.go.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper customObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();

    // JavaTimeModule을 ObjectMapper에 수동 등록
    objectMapper.registerModule(new JavaTimeModule());
    // LocalDate를 배열 대신 문자열("YYYY-MM-DD")로 변환하도록 설정
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    return objectMapper;
  }
}
