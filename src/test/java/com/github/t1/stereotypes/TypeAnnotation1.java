package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target(TYPE)
public @interface TypeAnnotation1 {
    public String value() default "";

    public int number() default 1;
}
