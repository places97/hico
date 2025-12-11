package kr.go.hico.ts.test.web;

import java.util.HashMap;
import java.util.Map;
import kr.go.hico.ts.test.svc.Test2Svc;
import kr.go.hico.ts.test.vo.Test2Vo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test2")
@Slf4j
public class Test2Ctl {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private Test2Svc test2Svc;

    @RequestMapping("/page.do")
    public String page(Test2Vo test2Vo, Model model) {

      Map<String, Object> dataSet = null;
      dataSet = new HashMap<String, Object>();

      dataSet.put("req", test2Vo);
      dataSet.put("res", null);

      model.addAttribute("dataSet", dataSet);

      return "hoontest";
    }
}
