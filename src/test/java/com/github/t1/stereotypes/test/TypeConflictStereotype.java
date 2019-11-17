package com.github.t1.stereotypes.test;

import com.github.t1.stereotypes.PropagateTo;

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
@TypeAnnotation2
@TypeAnnotation3
@TypeAnnotation4
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Generated("not really")
public @interface TypeConflictStereotype {
    @PropagateTo({ TypeAnnotation2.class, TypeAnnotation4.class })
    public int value();

    @PropagateTo(value = { TypeAnnotation3.class }, name = "value")
    public int value2();
}
