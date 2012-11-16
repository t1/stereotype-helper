package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@TypeStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(TYPE)
public @interface IndirectTestStereotype {
}
