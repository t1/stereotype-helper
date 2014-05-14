package com.github.t1.stereotypes.test;

import static org.junit.Assert.*;

import java.lang.annotation.*;

import javax.enterprise.inject.Stereotype;

import org.junit.Test;

import com.github.t1.stereotypes.Annotations;

public class TypeAnnotationsTest {

    private <T> T find(Class<T> type, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (type.isInstance(annotation)) {
                return type.cast(annotation);
            }
        }
        throw new RuntimeException("not found");
    }

    @Test
    public void directAnnotationShouldBePresent() {
        @TypeAnnotation1("test")
        class Target {}

        boolean present = Annotations.on(Target.class).isAnnotationPresent(TypeAnnotation1.class);

        assertTrue(present);
    }

    @Test(expected = NullPointerException.class)
    public void nullAnnotationShouldThrowNPE() {
        @TypeAnnotation1("test")
        class Target {}

        Annotations.on(Target.class).isAnnotationPresent(null);
    }

    @Test
    public void missingAnnotationShouldNotBePresent() {
        @TypeAnnotation1("test")
        class Target {}

        boolean present = Annotations.on(Target.class).isAnnotationPresent(Retention.class);

        assertFalse(present);
    }

    @Test
    public void stereotypeAnnotationShouldNotBePresent() {
        @TypeStereotype
        class Target {}

        boolean present = Annotations.on(Target.class).isAnnotationPresent(Stereotype.class);

        assertFalse(present);
    }

    @Test
    public void shouldGetDirectAnnotation() {
        @TypeAnnotation1("test")
        class Target {}

        TypeAnnotation1 annotation = Annotations.on(Target.class).getAnnotation(TypeAnnotation1.class);

        assertEquals("test", annotation.value());
    }

    @Test(expected = NullPointerException.class)
    public void gettingNullAnnotationShouldThrowNPE() {
        @TypeAnnotation1("test")
        class Target {}

        Annotations.on(Target.class).getAnnotation(null);
    }

    @Test
    public void missingAnnotationShouldGetNull() {
        @TypeAnnotation1("test")
        class Target {}

        Retention annotation = Annotations.on(Target.class).getAnnotation(Retention.class);

        assertNull(annotation);
    }

    @Test
    public void expandedAnnotationShouldBePresent() {
        @TypeStereotype()
        class Target {}

        boolean present = Annotations.on(Target.class).isAnnotationPresent(TypeAnnotation1.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetStereotypedAnnotation() {
        @TypeStereotype()
        class Target {}

        TypeAnnotation1 present = Annotations.on(Target.class).getAnnotation(TypeAnnotation1.class);

        assertEquals(1, present.number());
        assertEquals("stereotype-test", present.value());
    }

    @Test
    public void indirectlyExpandedAnnotationShouldBePresent() {
        @IndirectTestStereotype
        class Target {}

        boolean present = Annotations.on(Target.class).isAnnotationPresent(TypeAnnotation1.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() {
        @IndirectTestStereotype
        class Target {}

        TypeAnnotation1 annotation = Annotations.on(Target.class).getAnnotation(TypeAnnotation1.class);

        assertEquals("stereotype-test", annotation.value());
    }

    @Test
    public void shouldNotExpandTargetAnnotation() {
        @NoTargetStereotype
        class Target {}

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(2, annotations.length);
        assertEquals("no-target", find(NoTargetAnnotation.class, annotations).value());
        assertEquals("package-type-default", find(TypeAnnotation1.class, annotations).value());
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() {
        @TypeStereotype
        @TypeAnnotation1("overwritten-test")
        class Target {}

        TypeAnnotation1 annotation = Annotations.on(Target.class).getAnnotation(TypeAnnotation1.class);

        assertEquals("overwritten-test", annotation.value());
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() {
        @IndirectTestStereotype
        @TypeAnnotation1("overwritten-test")
        class Target {}

        TypeAnnotation1 annotation = Annotations.on(Target.class).getAnnotation(TypeAnnotation1.class);

        assertEquals("overwritten-test", annotation.value());
    }

    @Test
    public void shouldExpandMetaOrTypeStereotype() {
        @MetaStereotype
        class Target {}

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(2, annotations.length);
        int a = (annotations[0] instanceof TypeAnnotation1) ? 0 : 1;
        int b = (a + 1) % 2;
        assertEquals("meta-test", ((TypeAnnotation1) annotations[a]).value());
        assertEquals("meta-or-type-annotation", ((MetaOrTypeAnnotation) annotations[b]).value());
    }

    @Test
    public void shouldGetOverwritingDefault() {
        @OverwritingStereotype
        class Target {}

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("default", ((TypeAnnotation1) annotations[0]).value());
        assertEquals(3, ((TypeAnnotation1) annotations[0]).number());
    }

    @Test
    public void shouldGetOverwrittenOverwriting() {
        @OverwritingStereotype(value = "passed-in", number = 5)
        class Target {}

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("passed-in", ((TypeAnnotation1) annotations[0]).value());
        assertEquals(5, ((TypeAnnotation1) annotations[0]).number());
    }

    @Test
    public void shouldNotGetDefaultConflictingReturnTypeOverwrite() {
        @OverwritingStereotype2
        class Target {}

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(2, annotations.length);
        assertEquals("package-type-default", find(TypeAnnotation1.class, annotations).value());
        assertEquals(99, find(TypeAnnotation2.class, annotations).value());
    }

    @Test
    public void shouldResolveConflictingReturnTypeOverwrite() {
        @TypeConflictStereotype(value = 10, value2 = 20)
        class Target {}

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(4, annotations.length);
        assertEquals("stereotype-test", find(TypeAnnotation1.class, annotations).value());
        assertEquals(1, find(TypeAnnotation1.class, annotations).number());
        assertEquals(10, find(TypeAnnotation2.class, annotations).value());
        assertEquals(20, find(TypeAnnotation3.class, annotations).value());
        assertEquals(10, find(TypeAnnotation4.class, annotations).value());
    }

    @Test
    public void shouldDefaultToPackageAnnotation() {
        class Target {}

        TypeAnnotation1 annotation = Annotations.on(Target.class).getAnnotation(TypeAnnotation1.class);

        assertEquals("package-type-default", annotation.value());
    }
}
