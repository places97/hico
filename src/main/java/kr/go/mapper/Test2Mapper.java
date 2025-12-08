package kr.go.mapper;

import java.util.List;
import kr.go.vo.Test2Vo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Test2Mapper {

  List<Test2Vo> getList();

}
