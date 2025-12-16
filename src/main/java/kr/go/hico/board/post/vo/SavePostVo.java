package kr.go.hico.board.post.vo;

import kr.go.hico.cmm.cmm.vo.BaseWriter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePostVo extends BaseWriter {

  private Long postId;
  private Long boardId;
  private String title;
  private String content;
}
