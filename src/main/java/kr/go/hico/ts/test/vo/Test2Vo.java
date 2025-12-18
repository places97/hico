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

  @MaskData(type = "NAME") // [마스킹] 성명 (성 유지, 이름 중문/말미 마스킹)
  private String name;

  @MaskData(type = "EMAIL") // [마스킹] 이메일 (아이디 앞 2자리 제외 마스킹)
  private String email;

  @MaskData(type = "SSN") // [마스킹] 주민등록번호 (뒷자리 마스킹)
  private String ssn;

  @MaskData(type = "SSN_HASH") // [가명화] 주민등록번호 해시 (분석용 고유값)
  private String ssnHash;

  @MaskData(type = "PHONE") // [마스킹] 휴대폰번호 (가운데/뒷자리 마스킹)
  private String phone;

  @MaskData(type = "ID") // [마스킹] 로그인 ID (앞 3자리 제외 마스킹)
  private String loginId;

  @MaskData(type = "ID_HASH") // [가명화] 로그인 ID 해시 (식별자 가명화)
  private String loginIdHash;

  @MaskData(type = "ADDR") // [일반화] 주소 (시/군/구 단위 외 상세주소 삭제)
  private String address;

  @MaskData(type = "IP") // [일반화] 접속 IP (마지막 옥텟 대역 마스킹)
  private String lastLoginIp;

  @MaskData(type = "BIRTH") // [일반화] 생년월일 (연도 외 월/일 삭제)
  private String birthDate;

  @MaskData(type = "AGE") // [일반화] 나이 (10대, 20대 단위 범주화)
  private String age;

  private String password; // 암호화 대상 (비식별 처리 제외)

  private String authCd; // 공통 코드 (비식별 처리 제외)
}
