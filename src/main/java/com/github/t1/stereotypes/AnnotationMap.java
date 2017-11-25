package com.github.t1.stereotypes;

import java.lang.annotation.Annotation;
import java.util.*;

class AnnotationMap {
    private Map<Class<? extends Annotation>, Annotation> map = new HashMap<>();

    void put(Class<? extends Annotation> type, Annotation annotation) {
        map.put(type, annotation);
    }

    <T extends Annotation> T get(Class<T> type) {
        return type.cast(map.get(type));
    }

    Annotation[] toArray() {
        return map.values().toArray(new Annotation[map.size()]);
    }
}
