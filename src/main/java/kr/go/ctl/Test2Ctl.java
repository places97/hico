package kr.go.ctl;

import java.util.HashMap;
import java.util.Map;
import kr.go.svc.Test2Svc;
import kr.go.vo.Test2Vo;
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
