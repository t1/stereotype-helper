package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

/**
 * Like the {@link Target} annotation, but at runtime, not for the compiler. This is important to properly resolve
 * default annotations, e.g. when you have a annotation that's actually targeted at methods, but you want to be able to
 * annotate a class or package to define a default. You have to declare {@link Target} to include
 * {@link ElementType#TYPE TYPE} and/or {@link ElementType#PACKAGE PACKAGE} so the compiler accepts that. Still, you
 * don't want that annotation to be valid for the type itself. So can add a {@link TargetScope} annotation with
 * {@link ElementType#METHOD METHOD}.
 */
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface TargetScope {
    /** The list of types that this annotation property should be propagated to. */
    ElementType[] value();
}
