package com.github.t1.stereotypes;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * This is the main class for the library to read annotations: instead of calling {@link Class#getAnnotations()
 * getAnnotations()}, etc. on a class object, you call {@link Annotations#on(Class)} and then do everything just the
 * same but on this class.
 */
public abstract class Annotations implements AnnotatedElement {

    public static AnnotatedElement on(Class<?> container) {
        return new TypeAnnotations(container);
    }

    public static AnnotatedElement onField(Class<?> container, String fieldName) {
        return new FieldAnnotations(container, fieldName);
    }

    public static AnnotatedElement onMethod(Class<?> container, String methodName, Class<?>... parameterTypes) {
        return new MethodAnnotations(container, methodName, parameterTypes);
    }

    private Map<Class<? extends Annotation>, Annotation> annotationCache;

    protected abstract Map<Class<? extends Annotation>, Annotation> loadCache();

    private Map<Class<? extends Annotation>, Annotation> getCache() {
        if (annotationCache == null)
            annotationCache = loadCache();
        if (annotationCache == null)
            throw new NullPointerException(this.getClass().getSimpleName() + "#loadCache() returned null");
        return annotationCache;
    }

    protected Map<Class<? extends Annotation>, Annotation> getAnnotations(Class<?> type, Annotation[] annotations) {
        return resolveDefaults(type, resolveStereotypes(null, annotations));
    }

    private Map<Class<? extends Annotation>, Annotation> resolveDefaults(Class<?> type,
            Map<Class<? extends Annotation>, Annotation> annotations) {
        for (Annotation typeAnnotation : type.getDeclaredAnnotations()) {
            resolveDefault(annotations, typeAnnotation);
        }
        for (Annotation typeAnnotation : type.getPackage().getDeclaredAnnotations()) {
            resolveDefault(annotations, typeAnnotation);
        }
        return annotations;
    }

    private void resolveDefault(Map<Class<? extends Annotation>, Annotation> annotations, Annotation annotation) {
        if (resolvable(annotation) && !annotations.containsKey(annotation.annotationType())) {
            annotations.put(annotation.annotationType(), annotation);
        }
    }

    private boolean resolvable(Annotation annotation) {
        return !isStereotype(annotation) && allowedAtTarget(annotation);
    }

    private boolean isStereotype(Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(Stereotype.class);
    }

    private boolean allowedAtTarget(Annotation annotation) {
        ElementType[] targets = getTargetScope(annotation);
        if (targets == null)
            return true;
        return Arrays.asList(targets).contains(getAllowedAnnotationTarget());
    }

    /**
     * The {@link ElementType}s of the {@link TargetScope} annotation. Defaults to the {@link ElementType}s of the
     * {@link Target} annotation. If that also is <code>null</code>, return <code>null</code>.
     */
    private ElementType[] getTargetScope(Annotation annotation) {
        TargetScope targetScope = annotation.annotationType().getAnnotation(TargetScope.class);
        if (targetScope != null)
            return targetScope.value();
        Target target = annotation.annotationType().getAnnotation(Target.class);
        if (target == null)
            return null;
        return target.value();
    }

    protected abstract ElementType getAllowedAnnotationTarget();

    private Map<Class<? extends Annotation>, Annotation> resolveStereotypes(Annotation containerAnnotation,
            Annotation[] annotations) {
        Map<Class<? extends Annotation>, Annotation> result = new HashMap<>();
        for (Annotation annotation : annotations) {
            if (isStereotype(annotation)) {
                resolveStereotype(annotation, result);
            } else if (allowedAtTarget(annotation)) {
                // eventually overwrite stereotype annotations
                Class<? extends Annotation> annotationType = annotation.annotationType();
                result.put(annotationType, propagatedProperties(containerAnnotation, annotation));
            }
        }
        return result;
    }

    private void resolveStereotype(Annotation annotation, Map<Class<? extends Annotation>, Annotation> result) {
        Map<Class<? extends Annotation>, Annotation> resolved = resolveStereotypes(annotation,
                annotation.annotationType().getAnnotations());
        // only add annotations from steretypes that are not already set at this level
        for (Entry<Class<? extends Annotation>, Annotation> entry : resolved.entrySet()) {
            if (!result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * If there is a method in the container annotation with the same name as a method in the contained annotation, then
     * propagate the value of the container to the contained value (which is only a dummy).
     */
    private Annotation propagatedProperties(Annotation container, Annotation contained) {
        if (container == null)
            return contained;
        AnnotationProxyBuilder proxyBuilder = new AnnotationProxyBuilder(contained);
        for (Method method : contained.annotationType().getDeclaredMethods()) {
            assert method.getReturnType() != Void.TYPE : method + " returns void";
            assert method.getParameterTypes().length == 0 : method + " takes parameters";

            Method containerMethod = getContainerMethod(container, method);
            if (containerMethod == null)
                continue;
            Object containerValue = get(container, containerMethod);
            proxyBuilder.overwrite(method, containerValue);
        }
        return proxyBuilder.build();
    }

    private Object get(Object instance, Method method) {
        try {
            return method.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Method getContainerMethod(Annotation container, Method annotationMethod) {
        for (Method containerMethod : container.annotationType().getDeclaredMethods()) {
            assert containerMethod.getReturnType() != Void.TYPE : containerMethod + " returns void";
            assert containerMethod.getParameterTypes().length == 0 : containerMethod + " takes parameters";

            if (matches(annotationMethod, containerMethod)) {
                return containerMethod;
            }
        }
        return null;
    }

    private boolean matches(Method annotationMethod, Method containerMethod) {
        String name = propagatedName(annotationMethod);
        Class<?> returnType = annotationMethod.getReturnType();
        if (!name.equals(propagatedName(containerMethod)))
            return false;
        if (!returnType.equals(containerMethod.getReturnType()))
            return false;
        return matchesPropagatedType(annotationMethod, containerMethod);
    }

    private String propagatedName(Method containerMethod) {
        PropagateTo propagateTo = containerMethod.getAnnotation(PropagateTo.class);
        if (propagateTo == null || "".equals(propagateTo.name()))
            return containerMethod.getName();
        return propagateTo.name();
    }

    private boolean matchesPropagatedType(Method annotationMethod, Method containerMethod) {
        PropagateTo propagateTo = containerMethod.getAnnotation(PropagateTo.class);
        if (propagateTo == null)
            return true;
        for (Class<? extends Annotation> matchingType : propagateTo.value()) {
            if (annotationMethod.getDeclaringClass() == matchingType) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        if (annotationClass == null)
            throw new NullPointerException();
        return annotationClass.cast(getCache().get(annotationClass));
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        if (annotationClass == null)
            throw new NullPointerException();
        return getAnnotation(annotationClass) != null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return getCache().values().toArray(new Annotation[getCache().size()]);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return getAnnotations();
    }
}
