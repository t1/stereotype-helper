package com.github.t1.stereotypes;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
