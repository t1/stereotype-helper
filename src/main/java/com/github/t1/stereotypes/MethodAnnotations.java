package com.github.t1.stereotypes;

import java.lang.annotation.ElementType;
import java.lang.reflect.Method;

class MethodAnnotations extends Annotations {
    private static Method getMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        try {
            return container.getMethod(methodName, parameterTypes);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public MethodAnnotations(Method method) {
        super(method, method.getDeclaringClass());
    }

    public MethodAnnotations(Class<?> container, String methodName, Class<?>... parameterTypes) {
        this(getMethod(container, methodName, parameterTypes));
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.METHOD;
    }
}
