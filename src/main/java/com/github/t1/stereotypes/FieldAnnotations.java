package com.github.t1.stereotypes;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;

class FieldAnnotations extends Annotations {
    private static Field getField(Class<?> container, String fieldName) {
        try {
            return container.getField(fieldName);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public FieldAnnotations(Field field) {
        super(field, field.getDeclaringClass());
    }

    public FieldAnnotations(Class<?> container, String fieldName) {
        this(getField(container, fieldName));
    }

    @Override
    protected ElementType getAllowedAnnotationTarget() {
        return ElementType.FIELD;
    }
}
