package kr.go.hico.ts.test.vo;

import kr.go.annotation.MaskData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Test2Vo {

  private Long id;

  // 1. 성명 (JSON 정책: NAME_KR)
  @MaskData(type = "NAME")
  private String name;

  // 2. 이메일 (JSON 정책: EMAIL)
  @MaskData(type = "EMAIL")
  private String email;

  // 3. 주민등록번호 (JSON 정책: SSN)
  @MaskData(type = "SSN")
  private String ssn;

  // 4. 휴대폰번호 (JSON 정책: PHONE_KR)
  @MaskData(type = "PHONE")
  private String phone;

  // 5. 로그인 ID (JSON 정책: USER_ID - 2025년 신규 추가 정책)
  @MaskData(type = "ID")
  private String loginId;

  // 6. 주소 (JSON 정책: ADDR_CITY - 시/군/구까지만 노출)
  @MaskData(type = "ADDR")
  private String address;

  // 7. 접속 IP (JSON 정책: IP_ADDR - 마지막 대역 마스킹)
  @MaskData(type = "IP")
  private String lastLoginIp;

  // 8. 생년월일 (JSON 정책: BIRTH_DAY - 월/일 마스킹)
  @MaskData(type = "BIRTH")
  private String birthDate;

  private String password;

  private String authCd;
}
