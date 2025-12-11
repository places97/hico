package kr.go.hico.cd.sur.svc;

import java.util.List;
import kr.go.hico.cd.sur.vo.SurQuestionVo;

public interface SurQuestionSvc {
  List<SurQuestionVo> selSurQuestionBySurId(int surId);
}
