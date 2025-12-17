package kr.go.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import kr.go.annotation.MaskData;
import kr.go.policy.DeIdentifyPolicyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 간략 설명 : AOP 기반 비식별 조치(마스킹)
 * 상세 설명 :
 *  1. @MaskData 어노테이션이 선언된 DTO/VO의 필드를 자동으로 탐색하여 비식별화함.
 *  2. 외부 정책 파일(privacy-policy.json)을 로드하여 정책 기반으로 동작함.
 *  3. ResponseEntity, Collection(List/Set), 중첩된 내부 DTO까지 재귀적으로 탐색하여 처리함.
 *  4. 2025년 개인정보 비식별 조치 가이드라인(NAME, PHONE, SSN, ADDR, IP 등)을 준수함.
 *
 */
@Component
public class MaskDataUtil {

    @Autowired
    private DeIdentifyPolicyLoader policyLoader;

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
        } catch (Exception e) {
          // 로그 처리 생략
        }
      }
    }

    private String executePolicy(String original, String type) {
      if (original == null || original.isEmpty())
        return original;
      Map<String, String> policy = policyLoader.getPolicy(type);
      return applyMaskingPattern(original, policy.get("pattern"));
    }

    /**
     * 패턴별 상세 마스킹 로직 (8종)
     */
    private String applyMaskingPattern(String value, String pattern) {
      if (value == null || value.isEmpty())
        return value;

      switch (pattern) {
        case "NAME_KR": // 성명: 2글자(홍*), 3글자 이상(홍*동, 홍**동)
          if (value.length() <= 2)
            return value.replaceAll("(?<=.).", "*");
          return value.charAt(0) + "*".repeat(value.length() - 2) + value.charAt(
              value.length() - 1);

        case "PHONE_KR": // 휴대폰: 010-****-1234
          return value.replaceAll("(\\d{2,3})[- .]?\\d{3,4}[- .]?(\\d{4})", "$1-****-$2");

        case "EMAIL": // 이메일: ab***@domain.com
          return value.replaceAll("(?<=.{2}).(?=[^@]*?@)", "*");

        case "SSN": // 주민번호: 900101-1******
          return value.replaceAll("(\\d{6})[- .]?(\\d{1})\\d{6}", "$1-$2******");

        case "ADDR_CITY": // 주소: 서울특별시 강남구 ***
          String[] addrParts = value.split(" ");
          if (addrParts.length >= 2) {
            return addrParts[0] + " " + addrParts[1] + " " + "***";
          }
          return value.substring(0, Math.min(value.length(), 5)) + "***";

        case "IP_ADDR": // IP: 192.168.0.***
          return value.replaceAll("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\.\\d{1,3}", "$1.***");

        case "BIRTH_DAY": // 생년월일: 1990-**-**
          return value.replaceAll("(\\d{4})[- .]?\\d{2}[- .]?\\d{2}", "$1-**-**");

        case "USER_ID": // 아이디: admin -> adm**
          if (value.length() <= 3)
            return value.replaceAll("(?<=.{1}).", "*");
          return value.substring(0, 3) + "*".repeat(value.length() - 3);

        default: // 공통(절반 마스킹)
          int half = value.length() / 2;
          return value.substring(0, half) + "*".repeat(value.length() - half);
      }
    }
  }