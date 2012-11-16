package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Stereotype
@MethodAnnotation("stereotype-test")
@Retention(RUNTIME)
@Target({ METHOD, ANNOTATION_TYPE })
public @interface MethodStereotype {
}
