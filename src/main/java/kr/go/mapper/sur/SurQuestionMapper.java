package kr.go.mapper.sur;

import java.util.List;
import kr.go.vo.sur.SurQuestionVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurQuestionMapper {
  List<SurQuestionVo> selSurQuestionBySurId(int surId);
}
