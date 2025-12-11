package kr.go.hico.ts.test.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import kr.go.hico.ts.test.svc.Test2Svc;
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
@Api(tags = "AOP 마스킹 테스트", description = "AOP 마스킹 테스트")
public class Test2RestCtl {
  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private Test2Svc test2Svc;

  @GetMapping("/selMask.do")
  @ApiOperation(value = "마스킹 목록 TEST", notes = "마스킹 목록 TEST")
  public ResponseEntity<ResultVo> getList(/*ReqTestSelDto dto*/) {
    ResultVo resultVo = new ResultVo();
    log.debug("Connect DB Profile :: {}", activeProfile);

    //vo 변환
    //Test2Vo test2Vo = dto.toVo();
    List<Test2Vo> rst = test2Svc.getList();

    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }

  @GetMapping("/getMask.do/{id}")
  @ApiOperation(value = "마스킹 단건 TEST", notes = "마스킹 단건 TEST")
  public ResponseEntity<ResultVo> getById(
      @ApiParam(value = "아이디", required = true, example = "1")
      @PathVariable Long id) {
    ResultVo resultVo = new ResultVo();

    Test2Vo rst = test2Svc.getById(id);

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }
}
