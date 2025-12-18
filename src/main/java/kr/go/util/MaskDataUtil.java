package kr.go.util;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * 간략 설명 : 개인정보 마스킹(Masking) 알고리즘 전용 유틸리티
 * 상세 설명 :
 *  1. DeIdentifyProcessor(엔진)에 의해 호출되어 지정된 패턴에 따라 마스킹된 문자열을 반환함.
 *  2. 2025년 개인정보 비식별 조치 가이드라인에 따른 성명, 연락처, 이메일 등.
 *  3. 정규표현식을 활용하여 하이픈(-) 유무에 상관없이 유연한 데이터 치환을 지원함.
 * </pre>
 */
@Component
public class MaskDataUtil {

    /**
     * 패턴별 상세 마스킹 로직
     */
    public String applyMaskingPattern(String value, String pattern) {
      switch (pattern) {
        case "NAME_KR":
          if (value.length() <= 2) return value.replaceAll("(?<=.).", "*");
          return value.charAt(0) + "*".repeat(value.length() - 2) + value.charAt(value.length() - 1);
        case "PHONE_KR":
          return value.replaceAll("(\\d{2,3})[- .]?\\d{3,4}[- .]?(\\d{4})", "$1-****-$2");
        case "EMAIL":
          return value.replaceAll("(?<=.{2}).(?=[^@]*?@)", "*");
        case "SSN":
          return value.replaceAll("(\\d{6})[- .]?(\\d{1})\\d{6}", "$1-$2******");
        case "USER_ID":
          if (value.length() <= 3) return value.replaceAll("(?<=.{1}).", "*");
          return value.substring(0, 3) + "*".repeat(value.length() - 3);
        default: return value;
      }
    }
  }