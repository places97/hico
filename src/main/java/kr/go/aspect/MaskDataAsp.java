package kr.go.aspect;

import kr.go.util.MaskDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MaskDataAsp {
    @Autowired
    private MaskDataUtil maskDataUtil;

    @AfterReturning(
        pointcut = "execution(* kr.go..*Ctl.*(..))"
            , returning = "result"
    )
    public void afterReturn(JoinPoint jp, Object result) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getName();

        log.info("----------------------------------------------------");
        log.info("AOP - 비식별 처리 실행 ");
        log.info("마스킹 처리");
        log.info("----------------------------------------------------");

       maskDataUtil.process(result);
    }
}
