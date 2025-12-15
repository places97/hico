package kr.go.hico.cd.sur.vo;

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

  private Long surQstId;
  private Long surId;
  private String title;
  private String capabilitieCd;
  private String capabilitieDtlCd;

}
