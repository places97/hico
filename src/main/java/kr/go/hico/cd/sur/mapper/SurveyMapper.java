package kr.go.hico.cd.sur.mapper;

import java.util.List;
import kr.go.hico.cd.sur.vo.SurveyVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyMapper {
  List<SurveyVo> getList();

  void transactionTest(SurveyVo surveyVo);
}
