package kr.go.svc;

import java.util.ArrayList;
import java.util.List;
import kr.go.externalMapper.ExtTest2Mapper;
import kr.go.mapper.Test2Mapper;
import kr.go.vo.Test2Vo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Test2SvcImp implements Test2Svc {

  @Autowired
  private Test2Mapper test2Mapper;

  @Autowired
  private ExtTest2Mapper extTest2Mapper;

  @Override
  public List<Test2Vo> getList(Test2Vo test2Vo) {
    List<Test2Vo> mix = new ArrayList<>();
    //TEST
    List<Test2Vo> testList = List.of(
        new Test2Vo(1L, "홍길동",   "hong.gildong@example.com", "901231-1234567", "01012345678"),
        new Test2Vo(2L, "이순신",   "lee.sunsin@navy.com",      "850101-2345678", "01098765432"), // 하이픈 없는 번호 테스트
        new Test2Vo(3L, "유관순",   "ryu.gwansun@history.org",  "020301-4567890", "01011112222"),
        new Test2Vo(4L, "김가네집", "simple@test.com",          "770707-1000000", "01055554444"),
        new Test2Vo(5L, "박찬",     "small@email.com",          "990909-1999999", "01066667777")
    );
    /*
    List<Test2Vo> inner = test2Mapper.getList();

    log.info("------------------inner------------------");
    log.info(inner.toString());
    log.info("------------------------------------------");

    List<Test2Vo> external = extTest2Mapper.getList();

    log.info("------------------external---------------------");
    log.info(external.toString());
    log.info("------------------------------------------");

    mix.addAll(inner);
    mix.addAll(external);
     */
    return testList;
  }
}
