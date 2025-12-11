package kr.go.hico.cd.sur.web;

import java.util.HashMap;
import java.util.Map;
import kr.go.hico.cd.sur.vo.SurveyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sur")
@Slf4j
public class SurveyController {

  @RequestMapping("/page.do")
  public String page(SurveyVo surveyVo, Model model) {

    Map<String, Object> dataSet = null;
    dataSet = new HashMap<String, Object>();

    dataSet.put("req", surveyVo);
    dataSet.put("res", null);

    model.addAttribute("dataSet", dataSet);

    return "cd/sur/survey";
  }

}
