package kr.go.hico.ts.test.web;

import java.util.List;
import kr.go.enums.ResCd;
import kr.go.exception.CmmException;
import kr.go.hico.ts.test.svc.TestSvc;
import kr.go.hico.ts.test.vo.TestVo;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestRestCtl {
  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private TestSvc testSvc;


  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list() {
    ResultVo resultVo = new ResultVo();
    log.debug("Connect DB Profile :: {}", activeProfile);

    List<TestVo> rst = testSvc.getList();

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
    TestVo testVo = new TestVo();
    return ResponseEntity.ok(ResultVo.success(testVo));
  }
}
