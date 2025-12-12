package kr.go.hico.sm.user.vo;

import java.time.LocalDateTime;
import java.util.List;
import kr.go.annotation.MaskData;
import kr.go.hico.cd.sur.vo.SurQuestionVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

  private Long id;

  @MaskData(type = "NAME")
  private String name;

  @MaskData(type = "EMAIL")
  private String email;

  @MaskData(type = "SSN")
  private String ssn;

  @MaskData(type = "PHONE")
  private String phone;

  private String password;

  private String authCd;

}
