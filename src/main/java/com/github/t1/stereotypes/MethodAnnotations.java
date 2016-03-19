package com.github.t1.stereotypes;

import java.lang.annotation.ElementType;
import java.lang.reflect.*;
import java.util.*;

class MethodAnnotations extends Annotations {
    private static Map<Method, MethodAnnotations> cache = new HashMap<>();

    public static AnnotatedElement onMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        return onMethod(getMethod(container, methodName, parameterTypes));
    }

    private static Method getMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        try {
            return container.getMethod(methodName, parameterTypes);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static AnnotatedElement onMethod(Method method) {
        MethodAnnotations result = cache.get(method);
        if (result == null) {
            result = new MethodAnnotations(method);
            cache.put(method, result);
        }
        return result;
    }

    private MethodAnnotations(Method method) {
        super(method);
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.METHOD;
    }
}
