package kr.go.common.util;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * 간략 설명 : 일반화(Generalization) 및 범주화 처리 유틸리티
 * 상세 설명 :
 *  1. 상세 데이터를 상위 개념으로 뭉뚱그려(Aggregation) 식별성을 낮춤.
 *  2. 주소(시군구 단위 외 삭제), 나이(연령대 변환), IP(대역 변환) 등 범주화 기술 적용.
 *  3. 2025년 개인정보 비식별 기술 가이드의 '범주화' 및 '라운딩' 원칙을 준수하여 구현함.
 * </pre>
 */
@Component
public class GeneralizationUtil {
  public String convert(String value, String pattern) {
    if (value == null || value.isEmpty()) return value;

    switch (pattern) {
      case "ADDR_CITY": // 주소 일반화
        String[] parts = value.split(" ");
        return parts.length >= 2 ? parts[0] + " " + parts[1] + " ***" : value.substring(0, 5) + "***";
      case "IP_ADDR": // IP 대역 일반화
        return value.replaceAll("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\.\\d{1,3}", "$1.***");
      case "BIRTH_DAY": // 생년월일 일반화
        return value.replaceAll("(\\d{4})[- .]?\\d{2}[- .]?\\d{2}", "$1-**-**");
      case "AGE_GROUP": // 나이 범주화
        try {
          int age = Integer.parseInt(value.replaceAll("[^0-9]", ""));
          return (age / 10 * 10) + "대";
        } catch (Exception e) { return "연령미상"; }
      default: return value;
    }
  }
}
