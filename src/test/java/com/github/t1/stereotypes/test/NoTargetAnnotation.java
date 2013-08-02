package com.github.t1.stereotypes.test;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface NoTargetAnnotation {
    public String value() default "no-target";
}
