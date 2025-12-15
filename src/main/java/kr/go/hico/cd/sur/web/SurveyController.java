package kr.go.hico.cd.sur.web;

import java.util.List;
import kr.go.hico.cd.sur.service.SurveyService;
import kr.go.hico.cd.sur.vo.SurveyVo;
import kr.go.hico.cmm.cmm.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sur")
@Slf4j
public class SurveyController {

  @Autowired
  private SurveyService surveyService;

  @RequestMapping("/page.do")
  public String page(SurveyVo surveyVo, Model model) {

    model.addAttribute("req", surveyVo);

    return "cd/sur/survey";
  }

  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list() {
    ResultVo resultVo = new ResultVo();

    List<SurveyVo> rst = surveyService.getList();

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }

  @PostMapping("/transaction.do")
  public ResponseEntity<ResultVo> transaction() {
    ResultVo resultVo = new ResultVo();

    List<SurveyVo> rst = surveyService.transactionTest();

    resultVo.setSuccess(rst);

    return ResponseEntity.ok(resultVo);
  }
}
