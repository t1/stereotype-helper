package com.example.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface Stereotype {
}
