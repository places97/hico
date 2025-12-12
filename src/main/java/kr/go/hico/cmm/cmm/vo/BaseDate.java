package kr.go.hico.cmm.cmm.vo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDate {

  private LocalDate createdAt;
  private LocalDate updatedAt;
}
