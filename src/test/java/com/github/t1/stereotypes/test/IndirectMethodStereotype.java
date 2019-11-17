package com.github.t1.stereotypes.test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@MethodStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(METHOD)
public @interface IndirectMethodStereotype {
}
