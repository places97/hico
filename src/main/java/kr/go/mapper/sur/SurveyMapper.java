package kr.go.mapper.sur;

import java.util.List;
import kr.go.vo.sur.SurveyVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyMapper {
  List<SurveyVo> getList();
}
