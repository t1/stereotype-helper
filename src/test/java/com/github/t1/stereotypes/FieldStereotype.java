package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Stereotype
@FieldAnnotation("stereotype-test")
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE })
public @interface FieldStereotype {
}
