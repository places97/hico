package kr.go.vo;

import kr.go.annotation.MaskData;
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
public class Test2Vo {

  private Long id;

  @MaskData(type = "NAME")
  private String name;

  @MaskData(type = "EMAIL")
  private String email;

  @MaskData(type = "SSN")
  private String ssn;

  @MaskData(type = "PHONE")
  private String phone;
}
