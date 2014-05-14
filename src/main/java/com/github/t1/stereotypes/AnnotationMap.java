package com.github.t1.stereotypes;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.Map.Entry;

class AnnotationMap implements Iterable<Map.Entry<Class<? extends Annotation>, Annotation>> {
    Map<Class<? extends Annotation>, Annotation> map = new HashMap<>();

    public boolean containsKey(Class<? extends Annotation> type) {
        return map.containsKey(type);
    }

    public void put(Class<? extends Annotation> type, Annotation annotation) {
        map.put(type, annotation);
    }

    public void merge(AnnotationMap resolved) {
        for (Map.Entry<Class<? extends Annotation>, Annotation> entry : resolved) {
            if (!containsKey(entry.getKey())) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public <T extends Annotation> T get(Class<T> type) {
        return type.cast(map.get(type));
    }

    public Annotation[] toArray() {
        return map.values().toArray(new Annotation[map.size()]);
    }

    @Override
    public Iterator<Entry<Class<? extends Annotation>, Annotation>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
