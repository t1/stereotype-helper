package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.annotation.Generated;

import com.github.t1.stereotypes.Stereotype;

@Stereotype
@TypeAnnotation1("stereotype-test")
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Generated("not really")
public @interface TypeStereotype {
}
