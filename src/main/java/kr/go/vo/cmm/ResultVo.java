package kr.go.vo.cmm;

import kr.go.enums.ResCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {

  private Object data;	// 데이터
  private String resCd;	// 응답코드
  private String resMsg;	// 응답메시지

  /**
   * 성공 Response
   * @param response
   * @return
   */
  public static ResultVo success(Object response) {
    return ResultVo.builder()
        .data(response)
        .resCd(String.valueOf(ResCd.SUCCESS.getCode()))
        .resMsg(ResCd.SUCCESS.getMsg())
        .build();
  }

  /**
   * 실패 Response
   * @param resCd
   * @return
   * @param resCd
   */
  public static ResultVo fail(ResCd resCd) {
    return ResultVo.builder()
        .data(null)
        .resCd(String.valueOf(resCd.getCode()))
        .resMsg(resCd.getMsg())
        .build();
  }
}
