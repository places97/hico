package kr.go.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception 에러코드, 메시지 정보
 *
 * @author GunPyo
 *
 */
@Getter
@AllArgsConstructor
public enum ResCd {
  SUCCESS(HttpStatus.OK,0, "성공")
  , FAIL_GENERAL(HttpStatus.BAD_REQUEST,999, "오류가 발생하였습니다.")

  // 100 시스템공통(CM)
  , CM_IO(HttpStatus.INTERNAL_SERVER_ERROR,101,"서버 내부 I/O 처리 중 오류가 발생했습니다.")
  // 200 학습공동체(LC)
  // 300 학습콘텐츠(CR)
  // 400 학습모듈(MR)
  // 500 전문가추천(ER)
  // 600 인증(CE)
  // 700 성장포토폴리오(GP)
  // 800 역량진단(CD)
  // 900 시스템관리(SM)
;

  private final HttpStatus httpStatus;
  private int code;
  private String msg;

}
