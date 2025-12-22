package kr.go.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PointInsert {
  int value() default 10; // 기본 10점 또는 10% 적립을 가정
  String type() default "BOARD";
}
