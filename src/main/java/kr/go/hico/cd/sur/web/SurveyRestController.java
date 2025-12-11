package kr.go.hico.cd.sur.web;

import java.util.List;
import kr.go.hico.cd.sur.service.SurveyService;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import kr.go.hico.cd.sur.vo.SurveyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sur")
@Slf4j
public class SurveyRestController {

  @Autowired
  private SurveyService surveyService;

  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list() {
    ResultVo resultVo = new ResultVo();

    List<SurveyVo> rst = surveyService.getList();

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }
}
