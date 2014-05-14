package com.github.t1.stereotypes;

import java.lang.annotation.ElementType;

class TypeAnnotations extends Annotations {
    public TypeAnnotations(Class<?> container) {
        super(container, container);
        if (container == null)
            throw new NullPointerException("container class must not be null");
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.TYPE;
    }
}
