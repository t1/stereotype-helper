package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import com.github.t1.stereotypes.TargetScope;

@Retention(RUNTIME)
@Target({ METHOD, TYPE, ANNOTATION_TYPE, PACKAGE })
@TargetScope(METHOD)
public @interface MethodAnnotation2 {
    public String value() default "default";
}
