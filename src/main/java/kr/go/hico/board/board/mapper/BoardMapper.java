package kr.go.hico.board.board.mapper;

import kr.go.hico.board.board.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

  List<BoardVo> getBoardList();
}
