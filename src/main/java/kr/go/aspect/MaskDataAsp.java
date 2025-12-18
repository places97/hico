package kr.go.aspect;

import kr.go.processor.DeIdentifyProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
@Aspect
@Slf4j
public class MaskDataAsp {
  @Autowired
  private DeIdentifyProcessor deIdentifyProcessor;

    @AfterReturning(
        pointcut = "execution(* kr.go..*Controller.*(..))"
            , returning = "result"
    )
    public void afterReturn(JoinPoint jp, Object result) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getName();

      // 1. 결과가 null이거나 기본 타입이면 로그 출력 없이 종료 (성능 및 소음 방지)
      if (result == null || result instanceof String || result instanceof View) {
        return;
      }

      // 2. 실제 데이터 객체(VO, List, Map 등)일 때만 로그 출력
        log.info("----------------------------------------------------");
        log.info("AOP - 비식별 처리 실행 ");
        log.info("마스킹 처리");
        log.info("----------------------------------------------------");

      deIdentifyProcessor.process(result);
    }
}
