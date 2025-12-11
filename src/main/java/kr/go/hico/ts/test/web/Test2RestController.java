package kr.go.hico.ts.test.web;

import java.util.List;
import kr.go.hico.ts.test.service.Test2Service;
import kr.go.hico.ts.test.vo.Test2Vo;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test2")
@Slf4j
public class Test2RestController {
  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private Test2Service test2Service;

  @GetMapping("/selMask.do")
  public ResponseEntity<ResultVo> getList(/*ReqTestSelDto dto*/) {
    ResultVo resultVo = new ResultVo();
    log.debug("Connect DB Profile :: {}", activeProfile);

    //vo 변환
    //Test2Vo test2Vo = dto.toVo();
    List<Test2Vo> rst = test2Service.getList();

    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }

  @GetMapping("/getMask.do/{id}")
  public ResponseEntity<ResultVo> getById(
      @PathVariable Long id) {
    ResultVo resultVo = new ResultVo();

    Test2Vo rst = test2Service.getById(id);

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }
}
