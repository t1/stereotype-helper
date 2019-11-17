package com.github.t1.stereotypes.test;

import javax.annotation.Generated;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@TypeAnnotation1("stereotype-test")
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Generated("not really")
public @interface TypeStereotype {
}
