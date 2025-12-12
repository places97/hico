package kr.go.hico.board.post.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
@Slf4j
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list(Long boardId) {
    ResultVo resultVo = new ResultVo();

    List<PostVo> rst = postService.getPostListByBoardId(boardId);
    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }

  @GetMapping("/detail.do")
  public String getPostDetail(@RequestParam("id") Long postId, Model model) {
    PostVo postVo = postService.getPostById(postId);
    model.addAttribute("post", postVo);

    return "board/post";
  }
}
