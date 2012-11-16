package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.annotation.Generated;

import com.github.t1.stereotypes.Stereotype;

@Stereotype
@TypeAnnotation1(value = "dummy", number = 2)
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Generated("not really")
public @interface OverwritingStereotype {
    public String value() default "default";

    public int number() default 3;
}
