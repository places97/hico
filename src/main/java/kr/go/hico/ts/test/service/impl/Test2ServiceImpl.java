package kr.go.hico.ts.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import kr.go.hico.sm.user.vo.UserVo;
import kr.go.hico.ts.test.externalMapper.ExtTest2Mapper;
import kr.go.hico.ts.test.mapper.Test2Mapper;
import kr.go.hico.ts.test.service.Test2Service;
import kr.go.hico.ts.test.vo.Test2Vo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Test2ServiceImpl implements Test2Service {

  @Autowired
  private Test2Mapper test2Mapper;

  @Autowired
  private ExtTest2Mapper extTest2Mapper;

  @Override
  public List<Test2Vo> getList() {

    //TEST
    List<Test2Vo> testList = new ArrayList<>();

    testList.add(new Test2Vo(1L, "홍길동", "hong.gildong@example.com", "901231-1234567", "010-1234-5678",
        "hongid", "서울특별시 강남구 역삼동 123", "192.168.0.125", "1990-01-01", "password123", "USER"));

    testList.add(new Test2Vo(2L, "이순신", "lee.sunsin@navy.com", "850101-2345678", "01098765432",
        "leess", "경남 통영시 한산면", "172.16.254.1", "1985-12-12", "password123", "USER"));

    testList.add(new Test2Vo(3L, "유관순", "ryu.gwansun@history.org", "020301-4567890", "01011112222",
        "hero2025", "충남 천안시 동남구", "211.234.56.78", "2002-03-01", "password123", "USER"));

    testList.add(new Test2Vo(4L, "김철수", "simple@test.com", "770707-1000000", "01055554444",
        "kimcs", "경기도 용인시 수지구", "10.0.0.1", "1977-07-07", "password123", "USER"));

    testList.add(new Test2Vo(5L, "박찬", "small@email.com", "990909-1999999", "01066667777",
        "parkc", "부산광역시 해운대구", "127.0.0.1", "1999-09-09", "password123", "USER"));
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

  @Override
  public Test2Vo getById(Long id) {
    //TEST
    List<Test2Vo> testList = new ArrayList<>();

    testList.add(new Test2Vo(1L, "홍길동", "hong.gildong@example.com", "901231-1234567", "010-1234-5678",
        "hongid", "서울특별시 강남구 역삼동 123", "192.168.0.125", "1990-01-01", "password123", "USER"));

    testList.add(new Test2Vo(2L, "이순신", "lee.sunsin@navy.com", "850101-2345678", "01098765432",
        "leess", "경남 통영시 한산면", "172.16.254.1", "1985-12-12", "password123", "USER"));

    testList.add(new Test2Vo(3L, "유관순", "ryu.gwansun@history.org", "020301-4567890", "01011112222",
        "hero2025", "충남 천안시 동남구", "211.234.56.78", "2002-03-01", "password123", "USER"));

    testList.add(new Test2Vo(4L, "김철수", "simple@test.com", "770707-1000000", "01055554444",
        "kimcs", "경기도 용인시 수지구", "10.0.0.1", "1977-07-07", "password123", "USER"));

    testList.add(new Test2Vo(5L, "박찬", "small@email.com", "990909-1999999", "01066667777",
        "parkc", "부산광역시 해운대구", "127.0.0.1", "1999-09-09", "password123", "USER"));

    Test2Vo test2Vo = testList.stream()
        .filter(vo -> vo.getId().equals(id))
        .findFirst()
        .orElse(null);

    return test2Vo;
  }
}
