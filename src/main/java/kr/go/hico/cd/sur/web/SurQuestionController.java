package kr.go.hico.cd.sur.web;

import java.util.List;
import kr.go.hico.cd.sur.service.SurQuestionService;
import kr.go.hico.cd.sur.vo.SurQuestionVo;
import kr.go.hico.cmm.core.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/surQuestion")
@Slf4j
public class SurQuestionController {
  @Autowired
  private SurQuestionService surQuestionService;

  @GetMapping("/selSurQuestionBySurId.do/{surId}")
  public ResponseEntity<ResultVo> selSurQuestionBySurId(@PathVariable int surId) {
    ResultVo resultVo = new ResultVo();

    List<SurQuestionVo> rst = surQuestionService.selSurQuestionBySurId(surId);

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }
}
