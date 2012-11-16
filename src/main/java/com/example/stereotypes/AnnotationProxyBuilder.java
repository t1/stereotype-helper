package com.example.stereotypes;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * {@link #build() Builds} a {@Proxy dynamic proxy} to overwrite the values of some annotation methods. If there
 * are no {@link #overwrite(Method, Object) overwrites}, the original annotation is returned.
 */
class AnnotationProxyBuilder {

    private class AnnotationInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (overwrites.containsKey(method))
                return overwrites.get(method);
            return method.invoke(original, args);
        }
    }

    private final Annotation original;
    private final InvocationHandler handler;
    private final Map<Method, Object> overwrites = new HashMap<>();

    public AnnotationProxyBuilder(Annotation original) {
        this.original = original;
        this.handler = new AnnotationInvocationHandler();
    }

    public void overwrite(Method method, Object value) {
        overwrites.put(method, value);
    }

    public Annotation build() {
        ClassLoader classLoader = original.getClass().getClassLoader();
        Class<?>[] interfaces = new Class[] { original.annotationType(), Annotation.class };
        return (Annotation) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
