package kr.go.hico.ts.test.dto;

import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "사용자 고유 ID", required = true, example = "1")
  private Long id;

  @ApiModelProperty(value = "이름", required = false, example = "홍길동")
  private String name;

  @ApiModelProperty(value = "이메일", required = false, example = "hong.gildong@example.com")
  private String email;

  @ApiModelProperty(value = "주민등록번호", required = false, example = "901231-1029485")
  private String ssn;

  @ApiModelProperty(value = "전화번호", required = false, example = "010-1234-5678")
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