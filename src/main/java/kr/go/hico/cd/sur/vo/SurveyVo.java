package kr.go.hico.cd.sur.vo;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 설문
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVo {

  private Long surId;
  private String targetCd;
  private String title;
  private LocalDateTime startDt;
  private LocalDateTime endDt;

  private List<SurQuestionVo> surQuestionVoList;
}
