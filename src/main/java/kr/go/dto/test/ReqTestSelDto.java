package kr.go.dto.test;

import kr.go.annotation.MaskData;
import kr.go.vo.Test2Vo;
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
public class ReqTestSelDto {

  private Long id;

  @MaskData(type = "NAME")
  private String name;

  @MaskData(type = "EMAIL")
  private String email;

  @MaskData(type = "SSN")
  private String ssn;

  @MaskData(type = "PHONE")
  private String phone;

  /**
   * Convert Dto to Vo
   *
   * @return
   */
  public Test2Vo toVo() {
    Test2Vo test2Vo = new Test2Vo();
    test2Vo.setName(name);
    test2Vo.setEmail(email);
    test2Vo.setSsn(ssn);
    test2Vo.setPhone(phone);

    return test2Vo;
  }
}