package kr.go.hico.board.post.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchPostVo {

  private Long boardId;
  private Long postId;
}
