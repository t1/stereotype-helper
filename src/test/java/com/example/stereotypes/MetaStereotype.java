package com.example.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import com.example.stereotypes.Stereotype;

@Stereotype
@TestAnnotation("meta-test")
@MetaOrTypeAnnotation
@Retention(RUNTIME)
@Target(TYPE)
public @interface MetaStereotype {
}
