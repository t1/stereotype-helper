package com.github.t1.stereotypes.test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@FieldStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(FIELD)
public @interface IndirectFieldStereotype {
}
