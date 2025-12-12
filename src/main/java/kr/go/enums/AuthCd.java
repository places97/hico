package kr.go.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 권한(역할) 정보
 *
 * @author Hbyun
 */
@Getter
@AllArgsConstructor
public enum AuthCd {
  USER("ROLE_USER", "사용자"),
  ADMIN("ROLE_ADMIN", "관리자");

  private final String code;
  private final String name;
}
