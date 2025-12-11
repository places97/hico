package kr.go.hico.ts.test.mapper;

import java.util.List;
import kr.go.hico.ts.test.vo.Test2Vo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Test2Mapper {

  List<Test2Vo> getList();

}
