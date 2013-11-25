package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import com.github.t1.stereotypes.Stereotype;

@Stereotype
@MethodAnnotation("stereotype-test")
@Retention(RUNTIME)
@Target({ METHOD, ANNOTATION_TYPE })
public @interface MethodStereotype {
}