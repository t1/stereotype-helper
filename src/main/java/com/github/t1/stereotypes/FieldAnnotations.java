package com.github.t1.stereotypes;

import java.lang.annotation.ElementType;
import java.lang.reflect.*;
import java.util.*;

class FieldAnnotations extends Annotations {
    private static Map<Field, FieldAnnotations> cache = new HashMap<>();

    public static AnnotatedElement onField(Class<?> container, String fieldName) {
        return onField(getField(container, fieldName));
    }

    private static Field getField(Class<?> container, String fieldName) {
        try {
            return container.getField(fieldName);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static AnnotatedElement onField(Field field) {
        FieldAnnotations result = cache.get(field);
        if (result == null) {
            result = new FieldAnnotations(field);
            cache.put(field, result);
        }
        return result;
    }

    private FieldAnnotations(Field field) {
        super(field);
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.FIELD;
    }
}
