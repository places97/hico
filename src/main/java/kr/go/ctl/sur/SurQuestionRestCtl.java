package kr.go.ctl.sur;

import java.util.List;
import kr.go.svc.sur.SurQuestionSvc;
import kr.go.vo.cmm.ResultVo;
import kr.go.vo.sur.SurQuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/surQuestion")
@Slf4j
public class SurQuestionRestCtl {

  @Autowired
  private SurQuestionSvc surQuestionSvc;

  @GetMapping("/selSurQuestionBySurId.do/{surId}")
  public ResponseEntity<ResultVo> selSurQuestionBySurId(@PathVariable int surId) {
    ResultVo resultVo = new ResultVo();

    List<SurQuestionVo> rst = surQuestionSvc.selSurQuestionBySurId(surId);

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }
}
