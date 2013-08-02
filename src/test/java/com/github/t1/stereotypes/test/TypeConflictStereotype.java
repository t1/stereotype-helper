package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.annotation.Generated;

import com.github.t1.stereotypes.*;

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
