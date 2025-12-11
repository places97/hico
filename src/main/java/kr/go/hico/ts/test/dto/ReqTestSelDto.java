package kr.go.hico.ts.test.dto;

import kr.go.hico.ts.test.vo.Test2Vo;
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

  private String name;

  private String email;

  private String ssn;

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