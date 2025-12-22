package kr.go.hico.sm.board.web;

import org.springframework.ui.Model;
import kr.go.hico.cmm.core.vo.ResultVo;
import kr.go.hico.sm.board.service.BoardService;
import kr.go.hico.sm.board.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
@Slf4j
public class BoardController {

  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private BoardService boardSvc;

  @GetMapping("/board.do")
  public String board(Model model) {
    List<BoardVO> rst = boardSvc.getList();

    model.addAttribute("activeProfile", activeProfile);
    model.addAttribute("boardList", rst);

    return "board/board";
  }

  @GetMapping("/board/list.do")
  public ResponseEntity<ResultVo> list() {
    ResultVo resultVo = new ResultVo();
    log.debug("Connect DB Profile :: {}", activeProfile);

    List<BoardVO> rst = boardSvc.getList();
    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }
}
