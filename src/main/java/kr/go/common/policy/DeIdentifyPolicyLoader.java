package kr.go.common.policy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 간략 설명 : 개인정보 비식별 정책(JSON) 로드 및 캐싱 컴포넌트
 * 상세 설명 :
 *  1. 서버 기동 시 ClassPath 내 'privacy-policy.json' 파일을 읽어 메모리에 적재함.
 *  2. 2025년 개인정보 비식별 조치 가이드라인에 따른 8대 핵심 항목(NAME, PHONE, SSN 등)의 정책을 관리함.
 *  3. 하드코딩된 마스킹 로직 대신 외부 설정 기반의 정책 운영을 지원함 (pattern 로직 구현하긴 해야함).
 *  4. 정책 변경 시 소스 코드 재컴파일 없이 JSON 파일 수정만으로 대응 가능하도록 설계됨(pattern 로직 있는것에 한하여).
 *
 */
@Component
@Slf4j
public class DeIdentifyPolicyLoader {

  // 정책을 담아둘 메모리 캐시
  private Map<String, Map<String, String>> policies = new HashMap<>();

  /**
   * 의존성 주입 완료 후 JSON 파일을 읽어 메모리에 적재합니다.
   */
  @PostConstruct
  public void init() {
    ObjectMapper mapper = new ObjectMapper();
    // src/main/resources/privacy-policy.json 파일을 읽음
    Resource resource = new ClassPathResource("privacy-policy.json");

    try {
      if (resource.exists()) {
        policies = mapper.readValue(
            resource.getInputStream(),
            new TypeReference<Map<String, Map<String, String>>>() {}
        );
        log.info("[Privacy] 비식별 정책 로드 완료: " + policies.size() + "건");
      } else {
        log.error("[Privacy] 정책 파일(privacy-policy.json)을 찾을 수 없습니다.");
      }
    } catch (IOException e) {
      log.error("[Privacy] 정책 로드 중 오류 발생: " + e.getMessage());
      //e.printStackTrace();
    }
  }

  /**
   * 특정 타입에 대한 정책을 반환합니다.
   *
   * @param type (예: NAME, PHONE)
   * @return 정책 Map (없을 경우 빈 Map 반환)
   */
  public Map<String, String> getPolicy(String type) {
    // 정책이 없으면 빈 Map을 반환하여 NullPointerException 방지
    return policies.getOrDefault(type, Collections.emptyMap());
  }
}