package kr.go.hico.cmm.core.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

  @Value("${now.active}")
  private String nowActive;

  @RequestMapping("/")
  public String root(Model model) {
	  log.trace("spring init trace");
	  log.debug("spring init debug");
	  log.info("spring init info");
	  log.warn("spring init warn");
	  log.error("spring init error");
    System.out.println("nowActive ::::::::::::::::::"+nowActive);

    model.addAttribute("contentTitle", "메인 화면");

    return "index";
  }

}