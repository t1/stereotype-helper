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
        return new TypeAnnotations(container);
    }

    public static AnnotatedElement on(Field field) {
        return new FieldAnnotations(field);
    }

    public static AnnotatedElement onField(Class<?> container, String fieldName) {
        return new FieldAnnotations(container, fieldName);
    }

    public static AnnotatedElement on(Method method) {
        return new MethodAnnotations(method);
    }

    public static AnnotatedElement onMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        return new MethodAnnotations(container, methodName, parameterTypes);
    }

    private static final Logger log = LoggerFactory.getLogger(Annotations.class);

    private final AnnotatedElement annotated;
    private final Class<?> container;
    private AnnotationMap annotationCache;

    public Annotations(AnnotatedElement annotated, Class<?> container) {
        this.annotated = annotated;
        this.container = container;
    }

    private synchronized AnnotationMap getAnnotationMap() {
        if (annotationCache == null) {
            log.debug("resolve annotations on {}", annotated);
            annotationCache = new AnnotationLoader(annotated, container, getAllowedAnnotationTarget()).get();
            log.debug("resolved annotations: {}", annotationCache);
        }
        return annotationCache;
    }

    protected abstract ElementType getAllowedAnnotationTarget();

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> type) {
        if (type == null)
            throw new NullPointerException();
        return getAnnotationMap().get(type);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        if (annotationClass == null)
            throw new NullPointerException();
        return getAnnotation(annotationClass) != null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return getAnnotationMap().toArray();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return getAnnotations();
    }
}
