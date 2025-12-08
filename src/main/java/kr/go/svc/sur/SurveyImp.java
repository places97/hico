package kr.go.svc.sur;

import java.util.List;
import kr.go.mapper.sur.SurveyMapper;
import kr.go.vo.sur.SurveyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SurveyImp implements  SurveySvc {

  @Autowired
  private SurveyMapper surveyMapper;

  @Override
  public List<SurveyVo> getList() {
    return surveyMapper.getList();
  }
}
