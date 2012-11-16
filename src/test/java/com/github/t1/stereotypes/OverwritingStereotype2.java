package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.annotation.Generated;

import com.github.t1.stereotypes.Stereotype;

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
