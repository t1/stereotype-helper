package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@MethodStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(METHOD)
public @interface IndirectMethodStereotype {
}
