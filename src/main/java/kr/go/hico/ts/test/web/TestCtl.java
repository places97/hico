package kr.go.hico.ts.test.web;

import java.util.HashMap;
import java.util.Map;
import kr.go.hico.ts.test.svc.TestSvc;
import kr.go.hico.ts.test.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestCtl {
  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private TestSvc testSvc;

  @RequestMapping("/page.do")
  public String page(TestVo testVo, Model model) {

    Map<String, Object> dataSet = null;
    dataSet = new HashMap<String, Object>();

    dataSet.put("req", testVo);
    dataSet.put("res", null);

    model.addAttribute("dataSet", dataSet);

    return "test2";
  }


}
