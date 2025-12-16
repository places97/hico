package kr.go.hico.fragment.fragment.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.hico.ts.test.service.TestService;
import kr.go.hico.ts.test.vo.TestVo;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/fragment")
@Slf4j
public class FragmentController {
  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Autowired
  private TestService testService;

  @GetMapping("/selectFragmentList.do")
  public String selectFragmentList(Model model) {
	  log.info("/fragment/selectFragmentList");
	  
	  model.addAttribute("contentTitle", "fragment 테스트");

	  return "fragment/selectFragmentList";
  }

  @GetMapping("/selectFragmentListHtmlAjax.do")
  public String selectFragmentListHtmlAjax(TestVo testVO, Model model) {
	  List<TestVo> rst = new ArrayList<TestVo>();

	  TestVo testVo = new TestVo();
	  testVo.setId(Long.parseLong("1")+testVO.getPage());
	  testVo.setTestContent("콘텐츠 1");
	  testVo.setRegDate(LocalDateTime.now());
	  rst.add(testVo);
	  
	  testVo = new TestVo();
	  testVo.setId(Long.parseLong("2")+testVO.getPage());
	  testVo.setTestContent("콘텐츠 2");
	  testVo.setRegDate(LocalDateTime.now());
	  rst.add(testVo);

	  testVo = new TestVo();
	  testVo.setId(Long.parseLong("3")+testVO.getPage());
	  testVo.setTestContent("콘텐츠 3");
	  testVo.setRegDate(LocalDateTime.now());
	  rst.add(testVo);
	  
	  testVo = new TestVo();
	  testVo.setId(Long.parseLong("4")+testVO.getPage());
	  testVo.setTestContent("콘텐츠 4");
	  testVo.setRegDate(LocalDateTime.now());
	  rst.add(testVo);
	  
	  testVo = new TestVo();
	  testVo.setId(Long.parseLong("5")+testVO.getPage());
	  testVo.setTestContent("콘텐츠 5");
	  testVo.setRegDate(LocalDateTime.now());
	  rst.add(testVo);

	  // rst = new ArrayList<TestVo>();
	  model.addAttribute("rstList", rst);

	  // 페이징 처리 관련 s
	  int totalCount = 963;	// 전체 목록 건수
	  testVO.setTotalCount(totalCount);
	  testVO.setGroupSize(10);	// 페이징처리시에 나오는 페이지 개수 설정

	  model.addAttribute("pageVO", testVO);

      return "fragment/selectFragmentListFm :: selectListHtmlFm";
  }

}