package kr.go.hico.cmm.cmm.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

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