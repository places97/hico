package kr.go.hico.sm.post.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePostVo {

  private Long postId;
  private Long boardId;
  private String title;
  private String content;
}
