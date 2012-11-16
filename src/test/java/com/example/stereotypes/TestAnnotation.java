package com.example.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target(TYPE)
public @interface TestAnnotation {
    public String value() default "";

    public int number() default 1;
}
