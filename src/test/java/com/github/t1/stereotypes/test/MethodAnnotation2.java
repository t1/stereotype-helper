package com.github.t1.stereotypes.test;

import com.github.t1.stereotypes.TargetScope;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ METHOD, TYPE, ANNOTATION_TYPE, PACKAGE })
@TargetScope(METHOD)
public @interface MethodAnnotation2 {
    String value() default "default";
}
