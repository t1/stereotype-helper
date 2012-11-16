package com.github.t1.stereotypes;

import java.lang.annotation.Annotation;
import java.util.Map;

class TypeAnnotations extends Annotations {
    private final Class<?> container;

    public TypeAnnotations(Class<?> container) {
        this.container = container;
    }

    @Override
    protected Map<Class<? extends Annotation>, Annotation> loadCache() {
        return getAnnotations(null, container.getAnnotations());
    }
}
