package kr.go.common.aspect;

import java.lang.reflect.Field;
import kr.go.enums.ResCd;
import kr.go.exception.CmmException;
import kr.go.common.util.CustomSwearWordFilterUtil;
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

  private CustomSwearWordFilterUtil filter;

  // TODO: 추후 함수 이름규칙 비정해지면 수정
  // 모든 Service에서 insert 나 update 로 시작하는 모든 메서드
  @Pointcut("execution(* kr.go.hico.*.*.service.*Service.insert*(..)) || execution(* kr.go.hico.*.*.service.*Service.update*(..))")
  public void saveMethods() {
  }

  /**
   *
   * @param joinPoint
   */
  @Before("saveMethods()")
  public void filterSwearWords(JoinPoint joinPoint) {
    filter = new CustomSwearWordFilterUtil();

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
        log.info(
            "    인자[{}]: 타입={}, 값={}", i,
            arg != null ? arg.getClass().getSimpleName() : "null", arg
        );

        if (arg != null) {
          if (arg instanceof String) {
            // 1. 루트 레벨 String 인자 처리
            filter.filterAndLogSwearWord(arg, "Root Argument");
            throw new CmmException(ResCd.CM_ASP);
          } else if (!arg.getClass().isArray() && !arg.getClass().isPrimitive()) {
            // 2. VO/DTO 객체 내부 String 필드 처리 (리플렉션)
            filterStringFields(arg);
          }
        }
      }
    } else {
      log.info("  전달 인자: 없음");
    }
    log.info("======================================================");
  }

  /**
   *
   * @param obj
   */
  private void filterStringFields(Object obj) {
    Class<?> currentClass = obj.getClass();

    while (currentClass != null) {
      for (Field field : currentClass.getDeclaredFields()) {
        if (field.getType().equals(String.class)) {

          if (field.getName().contains("Yn")) {
            log.debug("    [{}]: *Yn 필드는 검사에서 제외합니다.", field.getName());
            continue; // 현재 필드를 건너뛰고 다음 필드로 이동
          }
          try {
            field.setAccessible(true);
            String str = (String) field.get(obj);
            if (str != null) {
              filter.filterAndLogSwearWord(str, field.getName());
              throw new CmmException(ResCd.CM_ASP);
            }
          } catch (IllegalAccessException e) {
            log.error("리플렉션 접근 오류: {}", e.getMessage());
          }
        }
      }
      currentClass = currentClass.getSuperclass();
    }
  }
}
