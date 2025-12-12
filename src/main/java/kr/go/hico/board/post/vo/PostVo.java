package kr.go.hico.board.post.vo;

import kr.go.hico.cmm.cmm.vo.BaseDate;
import kr.go.hico.cmm.cmm.vo.BaseWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostVo extends BaseWriter {

  private Long postId;
  private Long boardId;
  private String title;
  private String content;
  private String delYn;
  private Integer viewCount;
}
