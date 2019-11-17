package com.github.t1.stereotypes.test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, TYPE })
public @interface MetaOrTypeAnnotation {
    String value() default "meta-or-type-annotation";
}
