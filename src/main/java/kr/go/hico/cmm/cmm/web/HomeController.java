package kr.go.hico.cmm.cmm.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class)

  @RequestMapping("/")
  public String root() {
	  log.trace("spring init trace");
	  log.debug("spring init debug");
	  log.info("spring init info");
	  log.warn("spring init warn");
	  log.error("spring init error");
	  
    return "redirect:/home.do";
  }

  @GetMapping("/home.do")
  public String index(Model model) {
	  log.info("home page init");
    model.addAttribute("contentTitle", "메인 화면");
    return "index";
  }
}