package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import com.github.t1.stereotypes.TargetScope;

@Retention(RUNTIME)
@Target({ FIELD, TYPE, ANNOTATION_TYPE, PACKAGE })
@TargetScope(FIELD)
public @interface FieldAnnotation {
    public String value() default "default";
}
