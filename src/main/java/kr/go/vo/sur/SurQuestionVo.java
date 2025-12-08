package kr.go.vo.sur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 설문 문항
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SurQuestionVo {

  private int surQstId;
  private int surId;
  private String title;
  private String capabilitieCd;
  private String capabilitieDtlCd;

}
