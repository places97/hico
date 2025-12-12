package kr.go.hico.cmm.cmm.web;

import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD:src/main/java/kr/go/hico/cmm/cmm/web/HomeCtl.java
=======
import org.springframework.beans.factory.annotation.Value;
>>>>>>> origin/main:src/main/java/kr/go/hico/cmm/cmm/web/HomeController.java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
<<<<<<< HEAD:src/main/java/kr/go/hico/cmm/cmm/web/HomeCtl.java
public class HomeCtl {
=======
@Slf4j
public class HomeController {
>>>>>>> origin/main:src/main/java/kr/go/hico/cmm/cmm/web/HomeController.java

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

  @Value("${now.active}")
  private String nowActive;

  @GetMapping("/home.do")
  public String index(Model model) {
	  log.info("home page init");
    System.out.println("nowActive ::::::::::::::::::"+nowActive);
    model.addAttribute("contentTitle", "메인 화면");
    return "index";
  }
}