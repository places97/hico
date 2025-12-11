package kr.go.hico.cmm.cmm.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeCtl {

  @RequestMapping("/")
  public String root() {
    return "redirect:/home.do";
  }

  @GetMapping("/home.do")
  public String index(Model model) {
    model.addAttribute("contentTitle", "메인 화면");
    return "index";
  }
}