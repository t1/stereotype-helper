package com.github.t1.stereotypes;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Map;

class FieldAnnotations extends Annotations {

    private static Field getField(Class<?> container, String fieldName) {
        try {
            return container.getField(fieldName);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private final Field field;

    public FieldAnnotations(Class<?> container, String fieldName) {
        this(getField(container, fieldName));
    }

    public FieldAnnotations(Field field) {
        this.field = field;
    }

    @Override
    protected Map<Class<? extends Annotation>, Annotation> loadCache() {
        return getAnnotations(field.getDeclaringClass(), field.getAnnotations());
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.FIELD;
    }
}
