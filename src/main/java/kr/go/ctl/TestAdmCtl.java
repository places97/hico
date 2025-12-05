package kr.go.ctl;

import java.util.List;
import kr.go.svc.TestSvc;
import kr.go.vo.TestVo;
import kr.go.vo.cmm.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adm/test")
@Slf4j
public class TestAdmCtl {

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
}
