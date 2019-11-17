package com.github.t1.stereotypes.test;

import com.github.t1.stereotypes.TargetScope;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ FIELD, TYPE, ANNOTATION_TYPE, PACKAGE })
@TargetScope(FIELD)
public @interface FieldAnnotation {
    String value() default "default";
}
