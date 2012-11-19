package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target({ FIELD, TYPE, ANNOTATION_TYPE })
@TargetScope(FIELD)
public @interface FieldAnnotation {
    public String value() default "default";
}
