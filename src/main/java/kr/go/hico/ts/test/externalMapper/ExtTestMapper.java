package kr.go.hico.ts.test.externalMapper;

import java.util.List;
import kr.go.hico.ts.test.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExtTestMapper {
  List<TestVo> getList();

}
