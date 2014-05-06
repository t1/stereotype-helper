package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.enterprise.inject.Stereotype;

@FieldStereotype()
@Stereotype
@Retention(RUNTIME)
@Target(FIELD)
public @interface IndirectFieldStereotype {
}
