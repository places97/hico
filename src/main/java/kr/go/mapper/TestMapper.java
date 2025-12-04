package kr.go.mapper;

import java.util.List;
import kr.go.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

  List<TestVo> getList();

}
