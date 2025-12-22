package kr.go.hico.cmm.core.vo;

import kr.go.hico.ts.test.vo.PageVO;
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
public class ComVO{

  // 1. 사용자 기본 인증 정보 (Authentication)
  private String loginId;      // 로그인 사용자 ID
  private String userNm;       // 사용자 성명
  private String userIp;       // 접속자 IP (보안 로그용)

  // 2. 권한 및 상태 (Authorization & Status)
  private String userRole;     // 권한 코드 (예: ROLE_ADMIN, ROLE_USER)
  private String userStatus;   // 계정 상태 (예: 사용중, 휴면, 정지)
  private String authLevel;    // 권한 레벨 (1~10 등급 등세부 제어용)

  // 3. 조직/그룹 정보 (Organization)
  private String groupId;      // 소속 그룹/부서 ID
  private String groupNm;      // 소속 그룹/부서 명
  private String companyId;    // 멀티 테넌트 환경일 경우 회사 ID

}
