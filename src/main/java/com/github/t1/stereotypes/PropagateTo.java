package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

/**
 * If multiple annotations of a {@link javax.enterprise.inject.Stereotype Stereotype} have properties of the same name
 * <i>and</i> type, and you want them to be different, then you can use this annotation to disambiguate it... either by
 * type (<code>value</code>) or by <code>name</code>.
 * 
 * @see javax.enterprise.inject.Stereotype
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface PropagateTo {
    /** The list of types that this annotation property should be propagated to. */
    Class<? extends Annotation>[] value();

    /** The name of the property where this annotation property should be propagated to. */
    String name() default "";
}
