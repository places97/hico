package kr.go.svc.sur;

import java.util.List;
import kr.go.mapper.sur.SurQuestionMapper;
import kr.go.vo.sur.SurQuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SurQuestionSvcImp implements  SurQuestionSvc {

  @Autowired
  private SurQuestionMapper surQuestionMapper;

  @Override
  public List<SurQuestionVo> selSurQuestionBySurId(int surId) {
    return surQuestionMapper.selSurQuestionBySurId(surId);
  }
}
