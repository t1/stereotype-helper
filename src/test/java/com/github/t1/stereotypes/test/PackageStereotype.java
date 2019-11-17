package com.github.t1.stereotypes.test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@Retention(RUNTIME)
@Target({ METHOD, TYPE, ANNOTATION_TYPE, PACKAGE })
@MethodAnnotation2("package-stereotype")
@FieldAnnotation2("package-stereotype")
public @interface PackageStereotype {}
