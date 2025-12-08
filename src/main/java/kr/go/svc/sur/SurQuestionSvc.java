package kr.go.svc.sur;

import java.util.List;
import kr.go.vo.sur.SurQuestionVo;

public interface SurQuestionSvc {
  List<SurQuestionVo> selSurQuestionBySurId(int surId);
}
