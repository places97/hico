package kr.go.hico.sm.board.mapper;

import kr.go.hico.sm.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

  List<BoardVO> getBoardList();
}
