package kr.go.hico.ts.test.vo;

import java.time.LocalDateTime;

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
public class TestVo extends PageVO {

  private Long id;
  private String testContent;
  private LocalDateTime regDate;
}
