package com.github.t1.stereotypes.test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@FieldAnnotation("stereotype-test")
@Retention(RUNTIME)
@Target({ FIELD, TYPE, ANNOTATION_TYPE })
public @interface FieldStereotype {}
