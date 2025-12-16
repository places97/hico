package kr.go.aspect;

import kr.go.util.CustomSwearWordFilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 비속어 필터 aspect
 */
@Component
@Aspect
@Slf4j
public class SwearWordFilterAsp {

  // TODO: 서비스 컨벤션 나중에 물어보기
  // 모든 Service에서 insert로 시작하는 모든 메서드
  @Pointcut("execution(* kr.go.hico.*.*.service.*Service.insert*(..))")
  public void saveMethods() {
  }

  @Before("saveMethods()")
  public void filterSwearWords(JoinPoint joinPoint) {
    // 1. 실행되는 메서드의 시그니처(이름, 클래스 등)를 가져옵니다.
    String methodName = joinPoint.getSignature().toShortString();

    // 2. 메서드에 전달된 인자 배열을 가져옵니다.
    Object[] args = joinPoint.getArgs();

    // 3. 로깅을 수행합니다.
    log.info("======================================================");
    log.info("▶ Aspect Log: [메서드 호출 감지]");
    log.info("  호출 메서드: {}", methodName);

    // 4. 인자들을 순회하며 로깅
    if (args != null && args.length > 0) {
      log.info("  전달 인자 ({}) 개:", args.length);
      for (int i = 0; i < args.length; i++) {
        Object arg = args[i];
        // 인자 타입과 값을 로깅합니다. (인자가 null일 수 있으므로 처리)
        log.info("    인자[{}]: 타입={}, 값={}", i,
            arg != null ? arg.getClass().getSimpleName() : "null",
            arg);
      }
    } else {
      log.info("  전달 인자: 없음");
    }
    log.info("======================================================");
  }
}
