package kr.go.hico.ts.test.service;

import java.util.List;
import kr.go.hico.ts.test.vo.Test2Vo;

public interface Test2Service {

  public List<Test2Vo> getList();

  Test2Vo getById(Long id);
}
