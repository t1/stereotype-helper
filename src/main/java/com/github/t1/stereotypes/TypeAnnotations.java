package com.github.t1.stereotypes;

import java.lang.annotation.*;
import java.util.Map;

class TypeAnnotations extends Annotations {
    private final Class<?> container;

    public TypeAnnotations(Class<?> container) {
        this.container = container;
    }

    @Override
    protected Map<Class<? extends Annotation>, Annotation> loadCache() {
        return getAnnotations(container, container.getAnnotations());
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.TYPE;
    }
}
