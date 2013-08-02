package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import com.github.t1.stereotypes.Stereotype;

@TypeStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(TYPE)
public @interface IndirectTestStereotype {
}
