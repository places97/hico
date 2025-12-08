package kr.go.externalMapper;

import java.util.List;
import kr.go.vo.Test2Vo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExtTest2Mapper {
  List<Test2Vo> getList();

}
