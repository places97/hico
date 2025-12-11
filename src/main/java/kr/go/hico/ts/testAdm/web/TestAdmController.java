package kr.go.hico.ts.testAdm.web;

import java.util.List;
import kr.go.hico.ts.test.service.TestService;
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
@RequestMapping("/adm/test")
@Slf4j
public class TestAdmController {

  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private TestService testService;

  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list() {
    ResultVo resultVo = new ResultVo();
    log.debug("Connect DB Profile :: {}", activeProfile);

    List<TestVo> rst = testService.getList();

    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }
}
