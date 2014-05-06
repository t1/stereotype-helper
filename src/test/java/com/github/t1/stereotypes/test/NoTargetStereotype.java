package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.enterprise.inject.Stereotype;

@Stereotype
@Retention(RUNTIME)
@Target(TYPE)
@NoTargetAnnotation
public @interface NoTargetStereotype {
}
