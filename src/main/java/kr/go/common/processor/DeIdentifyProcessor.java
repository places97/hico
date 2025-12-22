package kr.go.common.processor;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import kr.go.common.annotation.MaskData;
import kr.go.common.policy.DeIdentifyPolicyLoader;
import kr.go.common.util.GeneralizationUtil;
import kr.go.common.util.MaskDataUtil;
import kr.go.common.util.PseudonymUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 간략 설명 : 통합 비식별 조치(De-Identification) 실행 엔진
 * 상세 설명 :
 *  1. 비식별 처리의 최상위 프로세서로서 객체 탐색 및 실행 전략을 관리함.
 *  2. DeIdentifyPolicyLoader를 통해 로드된 JSON 정책(action)을 분석하여 적절한 엔진을 호출함.
 *  3. 마스킹(MASK), 가명화(PSEUDONYM), 일반화(GENERALIZE) 기술을 중앙 집중식으로 제어함.
 *  4. 2025년 개인정보 비식별 조치 가이드라인의 표준 프로세스를 준수함.
 * </pre>
 * @see     MaskDataUtil
 * @see     PseudonymUtil
 * @see     GeneralizationUtil
 */
@Component
public class DeIdentifyProcessor {
  @Autowired
  private DeIdentifyPolicyLoader policyLoader;

  @Autowired
  private MaskDataUtil maskDataUtil; // 마스킹 비식별

  @Autowired
  private PseudonymUtil pseudonymUtil; // 가명화 비식별

  @Autowired
  private GeneralizationUtil generalizationUtil; // 일반화 비식별

  /**
   * 객체를 탐색하며 마스킹 처리 (재귀 호출)
   */
  public void process(Object target) {
    if (target == null)
      return;

    // 1. ResponseEntity 처리
    if (target instanceof ResponseEntity) {
      process(((ResponseEntity<?>) target).getBody());
      return;
    }

    Class<?> clazz = target.getClass();

    // 2. 기본 타입 및 시스템 클래스 스킵
    if (clazz.getName().startsWith("java.lang.") ||
        clazz.isPrimitive() ||
        target instanceof String ||
        target instanceof Number ||
        target instanceof Boolean ||
        clazz.getSimpleName().startsWith("$$EnhancerBySpringCGLIB$$")) {
      return;
    }

    // 3. Collection 처리
    if (target instanceof Collection) {
      for (Object item : (Collection<?>) target) {
        process(item);
      }
      return;
    }

    // 4. 필드 순회
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(target);
        if (value == null)
          continue;

        if (field.isAnnotationPresent(MaskData.class) && value instanceof String) {
          String type = field.getAnnotation(MaskData.class).type();
          String maskedValue = executePolicy((String) value, type);
          field.set(target, maskedValue);
        } else {
          process(value); // 중첩 객체 재귀
        }
      } catch (Exception e) {}
    }
  }

  private String executePolicy(String original, String type) {
    Map<String, String> policy = policyLoader.getPolicy(type);
    String action = policy.get("action");

    switch (action) {
      case "MASK":        return maskDataUtil.applyMaskingPattern(original, policy.get("pattern"));
      case "PSEUDONYM":   return pseudonymUtil.encrypt(original, policy.get("pattern"));
      case "GENERALIZE":  return generalizationUtil.convert(original, policy.get("pattern"));
      default:            return original;
    }
  }
}
