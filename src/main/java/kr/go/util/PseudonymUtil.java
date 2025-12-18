package kr.go.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 간략 설명 : 가명화(Pseudonymization) 처리 유틸리티
 * 상세 설명 :
 *  1. 원본 데이터를 식별할 수 없는 고유한 대체값(해시 등)으로 변환함.
 *  2. SHA-256 단방향 해시 알고리즘 등을 사용하여 데이터의 무결성과 비가역성을 보장함.
 *  3. 2025년 가이드라인에 따른 '식별자 가명화' 기술을 구현하여 통계 및 분석용 데이터 생성에 활용함.
 *  4. DeIdentifyProcessor에 의해 호출되어 변환된 가명값 결과만 반환하는 전용 엔진임.
 * </pre>
 */
@Component
public class PseudonymUtil {
  public String encrypt(String value, String pattern) {
    if (value == null || value.isEmpty()) return value;

    if ("SHA256".equals(pattern)) {
      try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
          String hex = Integer.toHexString(0xff & b);
          if (hex.length() == 1) hexString.append('0');
          hexString.append(hex);
        }
        // 실무적으로는 가명값의 일부만 사용하여 식별성을 더 낮춤
        return hexString.toString().substring(0, 16);
      } catch (Exception e) { return "HASH_ERROR"; }
    }
    return value;
  }
}