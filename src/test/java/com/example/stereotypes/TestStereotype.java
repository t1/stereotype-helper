package com.example.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.annotation.Generated;

import com.example.stereotypes.Stereotype;

@Stereotype
@TestAnnotation("stereotype-test")
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Generated("not really")
public @interface TestStereotype {
}
