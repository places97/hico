package kr.go.hico.cmm.cmm.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseWriter extends BaseDate {

  private Long writerId;
  private String writerName;
}
