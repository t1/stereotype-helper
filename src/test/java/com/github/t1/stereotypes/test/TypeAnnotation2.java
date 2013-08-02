package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target(TYPE)
public @interface TypeAnnotation2 {
    public int value() default 2;
}
