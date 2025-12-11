package kr.go.hico.cd.sur.mapper;

import java.util.List;
import kr.go.hico.cd.sur.vo.SurQuestionVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurQuestionMapper {
  List<SurQuestionVo> selSurQuestionBySurId(int surId);
}
