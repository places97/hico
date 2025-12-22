package kr.go.hico.sm.board.service.impl;

import kr.go.hico.sm.board.mapper.BoardMapper;
import kr.go.hico.sm.board.service.BoardService;
import kr.go.hico.sm.board.vo.BoardVO;
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
  public List<BoardVO> getList() {

    List<BoardVO> list;
    list = boardMapper.getBoardList();

    return list;
  }
}
