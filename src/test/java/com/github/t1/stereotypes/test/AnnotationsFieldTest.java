package com.github.t1.stereotypes.test;

import static org.junit.Assert.*;

import java.lang.annotation.*;

import javax.enterprise.inject.Stereotype;

import org.junit.Test;

import com.github.t1.stereotypes.Annotations;

public class AnnotationsFieldTest {
    @Test
    public void directAnnotationShouldBePresent() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        boolean present = Annotations.onField(Target.class, "field").isAnnotationPresent(FieldAnnotation.class);

        assertTrue(present);
    }

    @Test(expected = NullPointerException.class)
    public void nullAnnotationShouldThrowNPE() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        Annotations.onField(Target.class, "field").isAnnotationPresent(null);
    }

    @Test
    public void missingAnnotationShouldNotBePresent() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        boolean present = Annotations.onField(Target.class, "field").isAnnotationPresent(Retention.class);

        assertFalse(present);
    }

    @Test
    public void stereotypeAnnotationShouldNotBePresent() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        boolean present = Annotations.onField(Target.class, "field").isAnnotationPresent(Stereotype.class);

        assertFalse(present);
    }

    @Test
    public void shouldGetDirectAnnotation() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("default", annotation.value());
    }

    @Test(expected = NullPointerException.class)
    public void gettingNullAnnotationShouldThrowNPE() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        Annotations.onField(Target.class, "field").getAnnotation(null);
    }

    @Test
    public void missingAnnotationShouldGetNull() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        Retention annotation = Annotations.onField(Target.class, "field").getAnnotation(Retention.class);

        assertNull(annotation);
    }

    @Test
    public void shouldGetDirectAnnotations() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("default", ((FieldAnnotation) annotations[0]).value());
    }

    @Test
    public void expandedAnnotationShouldBePresent() {
        class Target {
            @FieldStereotype
            public String field;
        }

        boolean present = Annotations.onField(Target.class, "field").isAnnotationPresent(FieldAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void indirectlyExpandedAnnotationShouldBePresent() {
        class Target {
            @IndirectFieldStereotype
            public String field;
        }

        boolean present = Annotations.onField(Target.class, "field").isAnnotationPresent(FieldAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetExpandedAnnotations() {
        class Target {
            @FieldStereotype
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("stereotype-test", ((FieldAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() {
        class Target {
            @IndirectFieldStereotype
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("stereotype-test", ((FieldAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() {
        class Target {
            @FieldAnnotation("overwritten-test")
            @FieldStereotype
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("overwritten-test", ((FieldAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() {
        class Target {
            @FieldAnnotation("overwritten-test")
            @IndirectFieldStereotype
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("overwritten-test", ((FieldAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldDefaultToTypeAnnotation() {
        @FieldAnnotation("type-default")
        class Target {
            @SuppressWarnings("unused")
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("type-default", ((FieldAnnotation) annotations[0]).value());
    }
}
