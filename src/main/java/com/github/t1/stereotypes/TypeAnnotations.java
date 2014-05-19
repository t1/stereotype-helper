package com.github.t1.stereotypes;

import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

class TypeAnnotations extends Annotations {
    private static Map<Class<?>, TypeAnnotations> cache = new HashMap<>();

    static AnnotatedElement onType(Class<?> type) {
        TypeAnnotations result = cache.get(type);
        if (result == null) {
            result = new TypeAnnotations(type);
            cache.put(type, result);
        }
        return result;
    }

    TypeAnnotations(Class<?> container) {
        super(container);
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.TYPE;
    }
}
