package com.github.t1.stereotypes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

class FieldAnnotations extends Annotations {

    private final Field field;

    public FieldAnnotations(Class<?> container, String fieldName) {
        try {
            this.field = container.getField(fieldName);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Map<Class<? extends Annotation>, Annotation> loadCache() {
        return getAnnotations(null, field.getAnnotations());
    }
}
