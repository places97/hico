package kr.go.externalMapper;

import java.util.List;
import kr.go.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExtTestMapper {
  List<TestVo> getList();

}
