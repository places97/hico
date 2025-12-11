package kr.go.hico.ts.test.svc;

import java.util.List;
import kr.go.hico.ts.test.vo.Test2Vo;

public interface Test2Svc {

  public List<Test2Vo> getList();

  Test2Vo getById(Long id);
}
