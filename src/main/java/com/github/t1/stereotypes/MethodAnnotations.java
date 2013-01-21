package com.github.t1.stereotypes;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Map;

class MethodAnnotations extends Annotations {

    private static Method getMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        try {
            return container.getMethod(methodName, parameterTypes);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private final Method method;

    public MethodAnnotations(Method method) {
        this.method = method;
    }

    public MethodAnnotations(Class<?> container, String methodName, Class<?>... parameterTypes) {
        this(getMethod(container, methodName, parameterTypes));
    }

    @Override
    protected Map<Class<? extends Annotation>, Annotation> loadCache() {
        return getAnnotations(method.getDeclaringClass(), method.getAnnotations());
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.METHOD;
    }
}
