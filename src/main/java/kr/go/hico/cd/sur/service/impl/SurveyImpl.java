package kr.go.hico.cd.sur.service.impl;

import java.util.List;
import kr.go.hico.cd.sur.mapper.SurveyMapper;
import kr.go.hico.cd.sur.service.SurveyService;
import kr.go.hico.cd.sur.vo.SurveyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SurveyImpl implements SurveyService {

  @Autowired
  private SurveyMapper surveyMapper;

  @Override
  public List<SurveyVo> getList() {
    return surveyMapper.getList();
  }

  @Override
  public List<SurveyVo> transactionTest() {

    SurveyVo innerSurveyVo = new SurveyVo();
    innerSurveyVo.setTitle("innnner");
    innerSurveyVo.setTargetCd("innerCode");

    surveyMapper.transactionTest(innerSurveyVo);

    return List.of();
  }
}
