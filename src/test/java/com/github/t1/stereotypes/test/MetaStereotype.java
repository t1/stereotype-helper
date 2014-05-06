package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.enterprise.inject.Stereotype;

@Stereotype
@TypeAnnotation1("meta-test")
@MetaOrTypeAnnotation
@Retention(RUNTIME)
@Target(TYPE)
public @interface MetaStereotype {
}
