package com.github.t1.stereotypes.test;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface NoTargetAnnotation {
    String value() default "no-target";
}
