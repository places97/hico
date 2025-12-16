package kr.go.hico.sm.post.web;

import kr.go.hico.sm.post.vo.InsertPostVo;
import kr.go.hico.sm.post.vo.SearchPostVo;
import kr.go.hico.sm.post.vo.SavePostVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import kr.go.hico.sm.post.service.PostService;
import kr.go.hico.sm.post.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @PutMapping("/save.do")
  public ResponseEntity<ResultVo> save(@RequestBody SavePostVo savePostVo) {
    ResultVo resultVo = new ResultVo();
    boolean rst = postService.savePost(savePostVo);
    if (rst) {
      resultVo.setResMsg("게시글 저장 성공");
    } else {
      resultVo.setResMsg("게시글 저장 실패");
    }

    return ResponseEntity.ok(resultVo);
  }

  @RequestMapping("/detail.do")
  public String getPostDetail(SearchPostVo requestVo, Model model) {
    PostVo postVo = postService.getPostById(requestVo);
    model.addAttribute("post", postVo);
    return "board/post";
  }

  @RequestMapping("/insert.do")
  public String insert(Long boardId, Model model) {
    model.addAttribute("boardId", boardId);
    return "board/new-post";
  }

  @PostMapping("/insert.do")
  public ResponseEntity<ResultVo> insert(@RequestBody InsertPostVo insertPostVo) {
    ResultVo resultVo = new ResultVo();
    boolean isSuccess = postService.insertPost(insertPostVo);
    if (isSuccess) {
      resultVo.setSuccess(insertPostVo);
    }
    return ResponseEntity.ok(resultVo);
  }
}
