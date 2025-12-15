package kr.go.hico.board.post.web;

import kr.go.hico.board.post.vo.PostRequestVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import kr.go.hico.board.post.service.PostService;
import kr.go.hico.board.post.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@Slf4j
public class PostController {

  @Autowired
  private PostService postService;

  @PostMapping("/list.do")
  public ResponseEntity<ResultVo> list(@RequestBody Long boardId) {
    ResultVo resultVo = new ResultVo();

    List<PostVo> rst = postService.getPostListByBoardId(boardId);
    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }

  @PostMapping("/detail.do")
  public String getPostDetail(@RequestBody PostRequestVo requestVo, Model model) {
    PostVo postVo = postService.getPostById(requestVo);
    model.addAttribute("post", postVo);
    log.info(postVo.toString());
    return "board/post";
  }
}
