package kr.go.ctl.sur;

import java.util.List;
import kr.go.svc.sur.SurveySvc;
import kr.go.vo.cmm.ResultVo;
import kr.go.vo.sur.SurveyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sur")
@Slf4j
public class SurveyRestCtl {

  @Autowired
  private SurveySvc surveySvc;

  @GetMapping("/list.do")
  public ResponseEntity<ResultVo> list() {
    ResultVo resultVo = new ResultVo();

    List<SurveyVo> rst = surveySvc.getList();

    resultVo.setData(rst);

    return ResponseEntity.ok(resultVo);
  }
}
