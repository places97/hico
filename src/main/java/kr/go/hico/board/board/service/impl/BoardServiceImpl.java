package kr.go.hico.board.board.service.impl;

import kr.go.hico.board.board.mapper.BoardMapper;
import kr.go.hico.board.board.service.BoardService;
import kr.go.hico.board.board.vo.BoardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

  @Autowired
  private BoardMapper boardMapper;

  @Override
  public List<BoardVo> getList() {

    List<BoardVo> list;
    list = boardMapper.getBoardList();

    return list;
  }
}
