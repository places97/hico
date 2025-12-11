package kr.go.hico.cd.sur.service.impl;

import java.util.List;
import kr.go.hico.cd.sur.mapper.SurQuestionMapper;
import kr.go.hico.cd.sur.service.SurQuestionService;
import kr.go.hico.cd.sur.vo.SurQuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SurQuestionServiceImpl implements SurQuestionService {

  @Autowired
  private SurQuestionMapper surQuestionMapper;

  @Override
  public List<SurQuestionVo> selSurQuestionBySurId(int surId) {
    return surQuestionMapper.selSurQuestionBySurId(surId);
  }
}
