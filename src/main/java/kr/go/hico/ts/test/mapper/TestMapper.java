package kr.go.hico.ts.test.mapper;

import java.util.List;
import kr.go.hico.ts.test.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

  List<TestVo> getList();

}
