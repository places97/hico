package kr.go.ctl;

import java.util.List;
import kr.go.dto.test.ReqTestSelDto;
import kr.go.enums.ResCd;
import kr.go.exception.CmmException;
import kr.go.svc.Test2Svc;
import kr.go.vo.Test2Vo;
import kr.go.vo.cmm.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test2")
@Slf4j
public class Test2RestCtl {
  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private Test2Svc test2Svc;

  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list(ReqTestSelDto dto) {
    ResultVo resultVo = new ResultVo();
    log.debug("Connect DB Profile :: {}", activeProfile);

    //vo 변환
    Test2Vo test2Vo = dto.toVo();
    List<Test2Vo> rst = test2Svc.getList(test2Vo);

    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }

  @GetMapping("/error-test.do")
  public ResponseEntity<ResultVo> errTest() {
    log.error("CmmException 발생 테스트 ctl 진입");
    boolean test = true;
    if(test) {
      throw new CmmException(ResCd.CM_IO,"무조건 에러");
    }
    Test2Vo test2Vo = new Test2Vo();
    return ResponseEntity.ok(ResultVo.success(test2Vo));
  }
}
