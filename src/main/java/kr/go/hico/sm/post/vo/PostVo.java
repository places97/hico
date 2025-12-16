package kr.go.hico.sm.post.vo;

import kr.go.hico.cmm.cmm.vo.BaseWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostVo extends BaseWriter {

  private Long postId;
  private Long boardId;
  private String title;
  private String content;
  private String delYn;
  private Integer viewCount;
}
