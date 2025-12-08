package kr.go.util;

import java.lang.reflect.Field;
import java.util.Collection;
import kr.go.annotation.MaskData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MaskDataUtil {
  /**
   * 재귀적으로 객체를 탐색하며 @MaskData가 붙은 필드를 마스킹 처리합니다.
   * 또한, 중첩된 객체(다른 DTO/VO, Collection, ResponseEntity)도 재귀적으로 처리합니다.
   * @param target 원본 객체
   */
  public void process(Object target) {
    if (target == null) {
      return;
    }

    Class<?> clazz = target.getClass();

    // 1. ResponseEntity 타입 처리
    if (target instanceof ResponseEntity) {
      Object body = ((ResponseEntity<?>) target).getBody();
      process(body);
      return;
    }

    // 2. 무한 재귀 및 시스템/기본 클래스 방지 로직
    if (clazz.getName().startsWith("java.lang.") ||
        //clazz.getName().startsWith("org.springframework.") ||
        clazz.isPrimitive() ||
        target instanceof String ||
        target instanceof Number ||
        target instanceof Boolean ||
        clazz.getSimpleName().startsWith("$$EnhancerBySpringCGLIB$$")) {
      return;
    }

    // 3. Collection 타입 처리 (List<Test2Vo> 처리용)
    if (target instanceof Collection) {
      for (Object item : (Collection<?>) target) {
        process(item); // 리스트 내의 각 항목에 대해 재귀 호출
      }
      return;
    }

    // 4. 일반 DTO/VO 객체의 필드 순회 및 재귀 호출
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(target);

        if (field.isAnnotationPresent(MaskData.class) && value instanceof String) {
          // @MaskData가 붙은 String 필드 마스킹 및 값 변경
          String original = (String) value;
          String maskedValue = maskString(original, field.getAnnotation(MaskData.class).type());
          field.set(target, maskedValue);
        } else if (value != null) {
          // 중첩 객체에 대해 재귀 호출
          process(value);
        }

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        field.setAccessible(false);
      }
    }
  }

  /**
   * 마스킹 처리
   */
  private String maskString(String original, String type) {
    if (original == null || original.length() < 2) {
      return original;
    }

    switch (type) {
      case "NAME":
        int length = original.length();
        if (length == 2) {
          return original.charAt(0) + "*";
        } else {
          // 3글자 이상일 경우 첫 글자와 마지막 글자 사이를 *로 마스킹
          return original.charAt(0) + "*".repeat(length - 2) + original.charAt(length - 1);
        }
      case "EMAIL": // 이메일
        int atIndex = original.indexOf('@');
        if (atIndex > 1) {
          return original.substring(0, 1) + "***" + original.substring(atIndex);
        }
        return original;
      case "SSN": // 주민등록번호
        // 예: 901231-1234567 -> 901231-1******
        if (original.length() == 13 || original.length() == 14) { // '-' 포함 또는 미포함
          int dashIndex = original.indexOf('-');
          if (dashIndex == -1) {
            // '-'가 없으면 7번째 자리부터 마스킹 (0-based index 6)
            return original.substring(0, 6) + "*******";
          } else {
            // '-'가 있으면 '-' 다음 자리부터 마스킹 (0-based index 8)
            return original.substring(0, dashIndex + 2) + "******";
          }
        }
        return original; // 형식이 안 맞으면 그대로 반환
      case "PHONE": // 휴대폰 번호 (가운데 4자리 마스킹)
        String digitsOnly = original.replaceAll("-", "");

        if (digitsOnly.length() == 11) {
          String masked = digitsOnly.substring(0, 3)
              + "-****-"
              + digitsOnly.substring(7);
          return masked;
        } else {
          // 11자리가 아니면 기본 마스킹 적용
          int half = original.length() / 2;
          return original.substring(0, half) + "*".repeat(original.length() - half);
        }
      default:
        int half = original.length() / 2;
        return original.substring(0, half) + "*".repeat(original.length() - half);
    }
  }
}
