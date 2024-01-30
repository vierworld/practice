package ru.vw.practice.lesson1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
  int MAX_PRIORITY = 10;
  int MIN_PRIORITY = 1;
  int priority() default 5;
}
