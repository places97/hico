package kr.go.hico.cd.sur.service;

import java.util.List;
import kr.go.hico.cd.sur.vo.SurQuestionVo;

public interface SurQuestionService {
  List<SurQuestionVo> selSurQuestionBySurId(int surId);
}
