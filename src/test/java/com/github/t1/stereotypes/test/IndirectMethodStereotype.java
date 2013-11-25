package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import com.github.t1.stereotypes.Stereotype;

@MethodStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(METHOD)
public @interface IndirectMethodStereotype {
}