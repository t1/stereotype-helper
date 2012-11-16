package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

/**
 * If multiple annotations of a {@link Stereotype} have properties of the same name <i>and</i> type, then you can use
 * this annotation to disambiguate it... either by type or by name.
 * 
 * @see Stereotype
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface PropagateTo {
    /** The list of types that this annotation property should be propagated to. */
    Class<? extends Annotation>[] value();

    /** The name of the property where this annotation property should be propagated to. */
    String name() default "";
}
