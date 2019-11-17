package com.github.t1.stereotypes.test;

import javax.annotation.Generated;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Stereotype
@TypeAnnotation2(99)
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Generated("not really")
public @interface OverwritingStereotype2 {
    /** this value is not overwritten into the {@link TypeAnnotation2} above; wrong type */
    public String value() default "default";

    /** this number is not overwritten into the {@link TypeAnnotation2} above; wrong name */
    public int number() default 3;
}
