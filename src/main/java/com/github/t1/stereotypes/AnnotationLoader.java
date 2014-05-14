package com.github.t1.stereotypes;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Arrays;

import org.slf4j.*;

public class AnnotationLoader {
    private static final Class<? extends Annotation> STEREOTYPE = initStereotype();

    @SuppressWarnings("unchecked")
    private static Class<? extends Annotation> initStereotype() {
        try {
            return (Class<? extends Annotation>) Class.forName("javax.enterprise.inject.Stereotype");
        } catch (ClassNotFoundException e) {
            log.warn("no @Stereotype class found in classpath; disable stereotype support");
            return null;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(AnnotationLoader.class);

    private final ElementType allowedTarget;

    private final AnnotationMap result = new AnnotationMap();

    public AnnotationLoader(AnnotatedElement annotated, Class<?> container, ElementType allowedTarget) {
        this.allowedTarget = allowedTarget;
        resolvePackageStereotypes(null, annotated);
        resolveDeclaringMemberStereotypes(null, annotated);
        resolveStereotypes(null, annotated);
        resolveDefaults(container);
    }

    private void resolveStereotypes(Annotation containerAnnotation, AnnotatedElement annotated) {
        for (Annotation annotation : annotated.getAnnotations()) {
            if (isStereotype(annotation)) {
                log.debug("  resolve stereotype {}", annotation);
                resolveStereotypes(annotation, annotation.annotationType());
            }
        }
        for (Annotation annotation : annotated.getAnnotations()) {
            if (!isStereotype(annotation) && allowedAtTarget(annotation)) {
                // eventually overwrite stereotype annotations
                Annotation propagated = propagatedProperties(containerAnnotation, annotation);
                result.put(annotation.annotationType(), propagated);
            }
        }
    }

    private boolean isStereotype(Annotation annotation) {
        if (STEREOTYPE == null)
            return false;
        return annotation.annotationType().isAnnotationPresent(STEREOTYPE);
    }

    private boolean allowedAtTarget(Annotation annotation) {
        ElementType[] targets = getTargetScope(annotation);
        if (targets == null)
            return true;
        return Arrays.asList(targets).contains(allowedTarget);
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

    /**
     * If there is a property in the container annotation with the same name as a property in the contained annotation,
     * then propagate the value of the container to the contained value (which is only a dummy).
     */
    private Annotation propagatedProperties(Annotation container, Annotation contained) {
        if (container == null)
            return contained;
        log.debug("  propagate properties of {} into {}", contained, container);
        AnnotationProxyBuilder proxyBuilder = new AnnotationProxyBuilder(contained);
        for (Method property : contained.annotationType().getDeclaredMethods()) {
            assert property.getReturnType() != Void.TYPE : property + " returns void";
            assert property.getParameterTypes().length == 0 : property + " takes parameters";

            Method containerProperty = getContainerProperty(container, property);
            if (containerProperty == null)
                continue;
            Object containerValue = get(container, containerProperty);
            proxyBuilder.overwrite(property, containerValue);
        }
        return proxyBuilder.build();
    }

    private Method getContainerProperty(Annotation container, Method annotationProperty) {
        for (Method containerProperty : container.annotationType().getDeclaredMethods()) {
            assert containerProperty.getReturnType() != Void.TYPE : containerProperty + " returns void";
            assert containerProperty.getParameterTypes().length == 0 : containerProperty + " takes parameters";

            if (matchesPropagation(annotationProperty, containerProperty)) {
                return containerProperty;
            }
        }
        return null;
    }

    private boolean matchesPropagation(Method annotationProperty, Method containerProperty) {
        String name = propagatedName(annotationProperty);
        Class<?> returnType = annotationProperty.getReturnType();
        if (!name.equals(propagatedName(containerProperty)))
            return false;
        if (!returnType.equals(containerProperty.getReturnType()))
            return false;
        return matchesPropagatedType(annotationProperty, containerProperty);
    }

    private String propagatedName(Method containerProperty) {
        PropagateTo propagateTo = containerProperty.getAnnotation(PropagateTo.class);
        if (propagateTo == null || "".equals(propagateTo.name()))
            return containerProperty.getName();
        return propagateTo.name();
    }

    private boolean matchesPropagatedType(Method annotationProperty, Method containerProperty) {
        PropagateTo propagateTo = containerProperty.getAnnotation(PropagateTo.class);
        if (propagateTo == null)
            return true;
        for (Class<? extends Annotation> matchingType : propagateTo.value()) {
            if (annotationProperty.getDeclaringClass() == matchingType) {
                return true;
            }
        }
        return false;
    }

    private Object get(Object instance, Method method) {
        try {
            return method.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private void resolveDeclaringMemberStereotypes(Annotation containerAnnotation, AnnotatedElement annotated) {
        if (annotated instanceof Member) {
            Member member = (Member) annotated;
            resolveStereotypes(containerAnnotation, member.getDeclaringClass());
        }
    }

    private void resolvePackageStereotypes(Annotation containerAnnotation, AnnotatedElement annotated) {
        Class<?> type;
        if (annotated instanceof Member) {
            Member member = (Member) annotated;
            type = member.getDeclaringClass();
        } else {
            type = (Class<?>) annotated;
        }
        AnnotatedElement pkg = type.getPackage();
        resolveStereotypes(containerAnnotation, pkg);
    }

    private void resolveDefaults(Class<?> type) {
        for (Annotation typeAnnotation : type.getDeclaredAnnotations()) {
            resolveDefault(typeAnnotation);
        }
        Package package_ = type.getPackage();
        if (package_ != null) {
            for (Annotation typeAnnotation : package_.getDeclaredAnnotations()) {
                resolveDefault(typeAnnotation);
            }
        }
    }

    private void resolveDefault(Annotation annotation) {
        if (resolvable(annotation) && !result.containsKey(annotation.annotationType())) {
            result.put(annotation.annotationType(), annotation);
        }
    }

    private boolean resolvable(Annotation annotation) {
        return !isStereotype(annotation) && allowedAtTarget(annotation);
    }

    public AnnotationMap get() {
        return result;
    }
}
