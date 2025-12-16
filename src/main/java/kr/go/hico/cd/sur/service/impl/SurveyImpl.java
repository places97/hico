package kr.go.hico.cd.sur.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import kr.go.enums.ResCd;
import kr.go.exception.CmmException;
import kr.go.hico.cd.sur.mapper.SurveyMapper;
import kr.go.hico.cd.sur.service.SurveyService;
import kr.go.hico.cd.sur.vo.SurveyVo;
import kr.go.hico.ts.test.externalMapper.ExtTestMapper;
import kr.go.hico.ts.test.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SurveyImpl implements SurveyService {

  @Autowired
  private SurveyMapper surveyMapper;
  @Autowired
  private ExtTestMapper extTestMapper;

  @Override
  public List<SurveyVo> getList() {
    return surveyMapper.getList();
  }

  //TODO 내.외부 DB Transaction 동기화 관리
  // EX) JTA
  @Override
  //@Transactional("externalDBTransactionManager")
  public List<SurveyVo> transactionTest() {

    List<SurveyVo> rst = new ArrayList<>();
    SurveyVo innerSurveyVo = new SurveyVo();
    innerSurveyVo.setTitle("innnner");
    innerSurveyVo.setTargetCd("innerCode");

    surveyMapper.transactionTest(innerSurveyVo);

    Random random = new Random();

    TestVo testVo = new TestVo();
    testVo.setId(random.nextLong());
    testVo.setTestContent("AAAAAAAAAAAAAAA");

    extTestMapper.transaction(testVo);

    if(true)
      throw new CmmException(ResCd.FAIL_GENERAL);


    return rst;
  }
}
