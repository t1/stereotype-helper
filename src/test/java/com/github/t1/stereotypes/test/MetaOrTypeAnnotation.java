package com.github.t1.stereotypes.test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, TYPE })
public @interface MetaOrTypeAnnotation {
    public String value() default "meta-or-type-annotation";
}
