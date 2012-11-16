package com.example.stereotypes;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class Annotations {

    public static Annotations on(Class<?> container) {
        return new Annotations(container);
    }

    private final Map<Class<? extends Annotation>, Annotation> annotationCache;

    private Annotations(Class<?> container) {
        this.annotationCache = getAnnotations(null, container.getAnnotations());
    }

    private Map<Class<? extends Annotation>, Annotation> getAnnotations(Annotation containerAnnotation,
            Annotation[] annotations) {
        Map<Class<? extends Annotation>, Annotation> result = new HashMap<>();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (!inheritable(annotationType))
                continue;
            if (annotationType.isAnnotationPresent(Stereotype.class)) {
                inheritAnnotations(annotation, result);
            } else {
                // eventually overwrite inherited annotations
                result.put(annotationType, overwritten(containerAnnotation, annotation));
            }
        }
        return result;
    }

    /**
     * Annotations that are themselves only allowed on annotations (e.g. Retention, Target, Generated, or Stereotype
     * itself) are not inherited.
     */
    private boolean inheritable(Class<? extends Annotation> annotationType) {
        Target allowedTargets = annotationType.getAnnotation(Target.class);
        if (allowedTargets == null || allowedTargets.value().length != 1)
            return true;
        return allowedTargets.value()[0] != ElementType.ANNOTATION_TYPE;
    }

    private void inheritAnnotations(Annotation annotation, Map<Class<? extends Annotation>, Annotation> result) {
        Map<Class<? extends Annotation>, Annotation> inherited = getAnnotations(annotation,
                annotation.annotationType().getAnnotations());
        // only add inherited annotations that are not overwritten in this level
        for (Entry<Class<? extends Annotation>, Annotation> entry : inherited.entrySet()) {
            if (!result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * If there is a method in the container annotation with the same name as a method in the contained annotation, then
     * overwrite the contained value (which is only a dummy) with the value of the container.
     */
    private Annotation overwritten(Annotation container, Annotation contained) {
        if (container == null)
            return contained;
        AnnotationProxyBuilder proxyBuilder = new AnnotationProxyBuilder(contained);
        for (Method method : contained.annotationType().getDeclaredMethods()) {
            assert method.getReturnType() != Void.TYPE : method + " returns void";
            assert method.getParameterTypes().length == 0 : method + " takes parameters";

            Method containerMethod = getContainerMethod(container, method.getName(), method.getReturnType());
            if (containerMethod == null)
                continue;
            Object containerValue = get(container, containerMethod);
            proxyBuilder.overwrite(method, containerValue);
        }
        return proxyBuilder.build();
    }

    private Method getContainerMethod(Annotation container, String name, Class<?> returnType) {
        for (Method method : container.annotationType().getDeclaredMethods()) {
            assert method.getReturnType() != Void.TYPE : method + " returns void";
            assert method.getParameterTypes().length == 0 : method + " takes parameters";

            if (name.equals(method.getName()) && returnType.equals(method.getReturnType())) {
                return method;
            }
        }
        return null;
    }

    private Object get(Object instance, Method method) {
        try {
            return method.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        if (annotationClass == null)
            throw new NullPointerException();
        return annotationClass.cast(annotationCache.get(annotationClass));
    }

    public <A extends Annotation> boolean isAnnotationPresent(Class<A> annotationClass) {
        if (annotationClass == null)
            throw new NullPointerException();
        return getAnnotation(annotationClass) != null;
    }

    public Annotation[] getAnnotations() {
        return annotationCache.values().toArray(new Annotation[annotationCache.size()]);
    }
}
