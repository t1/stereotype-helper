package com.github.t1.stereotypes.test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@TypeStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(TYPE)
public @interface IndirectTestStereotype {
}
