package com.github.t1.stereotypes;

import java.lang.annotation.*;
import java.lang.reflect.*;

import org.slf4j.*;

/**
 * This is the main class for the library to read annotations: instead of calling {@link Class#getAnnotations()
 * getAnnotations()}, etc. on reflection objects, you call {@link Annotations#on(Class)} and then do everything just the
 * same with the {@link AnnotatedElement} returned.
 */
public abstract class Annotations implements AnnotatedElement {
    public static AnnotatedElement on(Class<?> container) {
        return TypeAnnotations.onType(container);
    }

    public static AnnotatedElement on(Field field) {
        return FieldAnnotations.onField(field);
    }

    public static AnnotatedElement onField(Class<?> container, String fieldName) {
        return FieldAnnotations.onField(container, fieldName);
    }

    public static AnnotatedElement on(Method method) {
        return MethodAnnotations.onMethod(method);
    }

    public static AnnotatedElement onMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        return MethodAnnotations.onMethod(container, methodName, parameterTypes);
    }

    private static final Logger log = LoggerFactory.getLogger(Annotations.class);

    private final AnnotatedElement annotated;
    private final AnnotationMap annotations;

    public Annotations(AnnotatedElement annotated) {
        this.annotated = annotated;
        this.annotations = loadAnnotations();
    }

    private AnnotationMap loadAnnotations() {
        log.debug("resolve annotations on {}", annotated);
        AnnotationMap annotations = new AnnotationLoader(annotated, getAllowedAnnotationTarget()).get();
        log.debug("resolved annotations: {}", annotations);
        return annotations;
    }

    protected abstract ElementType getAllowedAnnotationTarget();

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> type) {
        if (type == null)
            throw new NullPointerException();
        return annotations.get(type);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        if (annotationClass == null)
            throw new NullPointerException();
        return getAnnotation(annotationClass) != null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations.toArray();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return getAnnotations();
    }
}
