package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@FieldStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(FIELD)
public @interface IndirectFieldStereotype {
}
