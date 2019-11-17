package com.github.t1.stereotypes.test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE, PACKAGE })
public @interface TypeAnnotation1 {
    String value() default "";

    int number() default 1;
}
