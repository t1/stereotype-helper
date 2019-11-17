package com.github.t1.stereotypes.test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@TypeAnnotation1("meta-test")
@MetaOrTypeAnnotation
@Retention(RUNTIME)
@Target(TYPE)
public @interface MetaStereotype {}
