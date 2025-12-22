  package kr.go.common.aspect;

  import kr.go.common.annotation.PointInsert;
  import lombok.extern.slf4j.Slf4j;
  import org.aspectj.lang.JoinPoint;
  import org.aspectj.lang.annotation.AfterReturning;
  import org.aspectj.lang.annotation.Aspect;
  import org.aspectj.lang.reflect.MethodSignature;
  import org.springframework.stereotype.Component;

  @Component
  @Aspect
  @Slf4j
  public class PointInsertAsp {

    @AfterReturning(
        value = "@annotation(kr.go.common.annotation.PointInsert)",
        returning = "result"
    )
    public void afterMethodExecution(JoinPoint joinPoint, Object result) {
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      String methodName = signature.getName();

      PointInsert annotation = signature.getMethod().getAnnotation(PointInsert.class);
      int accumulationRate = annotation.value();

      log.info("----------------------------------------------------");
      log.info("AOP - 포인트 적립 로직 실행 ");
      log.info("실행된 메서드: {}", methodName);
      log.info("지정된 적립 비율: {}", accumulationRate);
      log.info("포인트 적립 완료 ");
      log.info("----------------------------------------------------");
    }
  }
