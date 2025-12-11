package kr.go.hico.ts.test.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.go.hico.sm.user.vo.UserVo;
import kr.go.hico.ts.test.service.Test2Service;
import kr.go.hico.ts.test.vo.Test2Vo;
import kr.go.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test2")
@Slf4j
public class Test2Controller {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private Test2Service test2Service;

    @RequestMapping("/page.do")
    public String page(Test2Vo test2Vo, Model model) {
      model.addAttribute("test2Vo", test2Vo);

      return "hoontest";
    }

  @GetMapping("/selMask.do")
  public @ResponseBody List<Test2Vo> getList() {
    log.debug("Connect DB Profile :: {}", activeProfile);

    UserVo userVo = SecurityUtil.getCurrentUser();
    log.debug("UserVo :: {}", userVo);

    List<Test2Vo> rst = test2Service.getList();

    return rst;
  }

  @GetMapping("/getMask.do/{id}")
  public @ResponseBody Test2Vo getById(@PathVariable Long id) {

    Test2Vo rst = test2Service.getById(id);

    return rst;
  }
}
