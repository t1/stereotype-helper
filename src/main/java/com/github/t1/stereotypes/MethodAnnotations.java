package com.github.t1.stereotypes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class MethodAnnotations extends Annotations {

    private final Method method;

    public MethodAnnotations(Class<?> container, String methodName, Class<?>... parameterTypes) {
        try {
            this.method = container.getMethod(methodName, parameterTypes);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Map<Class<? extends Annotation>, Annotation> loadCache() {
        return getAnnotations(null, method.getAnnotations());
    }
}
