package kr.go.hico.sm.post.vo;

import lombok.AllArgsConstructor;
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
public class InsertPostVo {

  private Long boardId;
  private Long userId;
  private String title;
  private String content;
  private String delYn;
}
