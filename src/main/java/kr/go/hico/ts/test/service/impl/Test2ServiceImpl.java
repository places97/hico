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

    // 1. 표준 데이터 (모든 포맷 정상)
    testList.add(new Test2Vo(
        1L, "홍길동", "hong.gd@example.com", "900101-1234567", "900101-1234567",
        "010-1234-5678", "hongid", "hongid", "서울특별시 구로구 구로동 123",
        "192.168.0.11", "1990-01-01", "35", "pass123", "USER"
    ));

    // 2. 하이픈 없는 데이터 및 2글자 성명 테스트
    testList.add(new Test2Vo(
        2L, "이순", "lee.sun@navy.com", "8505052345678", "8505052345678",
        "01098765432", "leess", "leess", "전라남도 여수시 중앙동",
        "172.16.254.1", "19851212", "40", "pass456", "USER"
    ));

    // 3. 4글자 성명 및 긴 아이디/복잡한 IP 테스트
    testList.add(new Test2Vo(
        3L, "독고영재", "dokgo.young@gmail.com", "020301-4567890", "020301-4567890",
        "010-5555-4444", "dokgo2025", "dokgo2025", "경기도 수원시 팔달구 매산로 1",
        "211.234.56.78", "2002.03.01", "23", "pass789", "USER"
    ));

    // 4. 짧은 데이터 (마스킹 경계값 테스트)
    testList.add(new Test2Vo(
        4L, "김철", "k@test.com", "770707-1000000", "770707-1000000",
        "02-123-4567", "abc", "abc", "인천광역시",
        "10.0.0.1", "1977-07-07", "48", "pass000", "USER"
    ));

    // 5. 공백 및 특수기호 포함 테스트
    testList.add(new Test2Vo(
        5L, "박 찬", "chan_park@email.com", "990909-1999999", "990909-1999999",
        "010 666 7777", "parkchan", "parkchan", "부산광역시 해운대구 우동",
        "127.0.0.1", "1999-09-09", "26", "pass111", "USER"
    ));
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

    // 1. 표준 데이터 (모든 포맷 정상)
    testList.add(new Test2Vo(
        1L, "홍길동", "hong.gd@example.com", "900101-1234567", "900101-1234567",
        "010-1234-5678", "hongid", "hongid", "서울특별시 구로구 구로동 123",
        "192.168.0.11", "1990-01-01", "35", "pass123", "USER"
    ));

    // 2. 하이픈 없는 데이터 및 2글자 성명 테스트
    testList.add(new Test2Vo(
        2L, "이순", "lee.sun@navy.com", "8505052345678", "8505052345678",
        "01098765432", "leess", "leess", "전라남도 여수시 중앙동",
        "172.16.254.1", "19851212", "40", "pass456", "USER"
    ));

    // 3. 4글자 성명 및 긴 아이디/복잡한 IP 테스트
    testList.add(new Test2Vo(
        3L, "독고영재", "dokgo.young@gmail.com", "020301-4567890", "020301-4567890",
        "010-5555-4444", "dokgo2025", "dokgo2025", "경기도 수원시 팔달구 매산로 1",
        "211.234.56.78", "2002.03.01", "23", "pass789", "USER"
    ));

    // 4. 짧은 데이터 (마스킹 경계값 테스트)
    testList.add(new Test2Vo(
        4L, "김철", "k@test.com", "770707-1000000", "770707-1000000",
        "02-123-4567", "abc", "abc", "인천광역시",
        "10.0.0.1", "1977-07-07", "48", "pass000", "USER"
    ));

    // 5. 공백 및 특수기호 포함 테스트
    testList.add(new Test2Vo(
        5L, "박 찬", "chan_park@email.com", "990909-1999999", "990909-1999999",
        "010 666 7777", "parkchan", "parkchan", "부산광역시 해운대구 우동",
        "127.0.0.1", "1999-09-09", "26", "pass111", "USER"
    ));

    Test2Vo test2Vo = testList.stream()
        .filter(vo -> vo.getId().equals(id))
        .findFirst()
        .orElse(null);

    return test2Vo;
  }
}
