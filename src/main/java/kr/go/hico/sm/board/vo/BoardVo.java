package kr.go.hico.sm.board.vo;

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
public class BoardVo extends BaseWriter {

  private Long boardId;
  private Long authId;
  private String boardName;
  private String boardDescription;
  private String useYn;
}
