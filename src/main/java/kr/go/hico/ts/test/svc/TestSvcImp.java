package kr.go.hico.ts.test.svc;

import java.util.ArrayList;
import java.util.List;
import kr.go.annotation.PointInsert;
import kr.go.hico.ts.test.externalMapper.ExtTestMapper;
import kr.go.hico.ts.test.mapper.TestMapper;
import kr.go.hico.ts.test.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestSvcImp implements TestSvc {

  @Autowired
  private TestMapper testMapper;

  @Autowired
  private ExtTestMapper extTestMapper;

  @Override
  @PointInsert(value = 50)
  public List<TestVo> getList() {
    List<TestVo> mix = new ArrayList<>();
    List<TestVo> inner = testMapper.getList();

    log.info("------------------inner------------------");
    log.info(inner.toString());
    log.info("------------------------------------------");

    List<TestVo> external = extTestMapper.getList();

    log.info("------------------external---------------------");
    log.info(external.toString());
    log.info("------------------------------------------");

    mix.addAll(inner);
    mix.addAll(external);
    return mix;
  }
}
