package com.github.t1.stereotypes.test;

import static org.junit.Assert.*;

import java.lang.annotation.Retention;
import java.lang.reflect.AnnotatedElement;

import javax.enterprise.inject.Stereotype;

import org.junit.Test;

import com.github.t1.stereotypes.Annotations;

public class FieldAnnotationsTest {
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

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("default", annotation.value());
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

        FieldAnnotation annotations = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("stereotype-test", annotations.value());
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() {
        class Target {
            @IndirectFieldStereotype
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("stereotype-test", annotation.value());
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() {
        class Target {
            @FieldStereotype
            @FieldAnnotation("overwritten-test")
            public String field;
        }

        FieldAnnotation annotations = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("overwritten-test", annotations.value());
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() {
        class Target {
            @IndirectFieldStereotype
            @FieldAnnotation("overwritten-test")
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("overwritten-test", annotation.value());
    }

    @Test
    public void shouldDefaultToTypeAnnotation() {
        @FieldAnnotation("type-default")
        class Target {
            @SuppressWarnings("unused")
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertEquals("type-default", annotation.value());
    }

    @Test
    public void shouldInheritFieldAnnotationFromTypeStereotype() {
        @FieldStereotype
        class Target {
            @SuppressWarnings("unused")
            public String foo;
        }

        AnnotatedElement onField = Annotations.onField(Target.class, "foo");

        FieldAnnotation fieldAnnotation = onField.getAnnotation(FieldAnnotation.class);

        assertEquals("stereotype-test", fieldAnnotation.value());
    }

    @Test
    public void shouldInheritFieldAnnotationFromPackage() {
        class Target {
            @SuppressWarnings("unused")
            public String foo;
        }

        AnnotatedElement onField = Annotations.onField(Target.class, "foo");

        FieldAnnotation fieldAnnotation = onField.getAnnotation(FieldAnnotation.class);

        assertEquals("package-field-default", fieldAnnotation.value());
    }

    @Test
    public void shouldInheritFieldAnnotationFromPackageStereotype() {
        class Target {
            @SuppressWarnings("unused")
            public String foo;
        }

        AnnotatedElement onField = Annotations.onField(Target.class, "foo");

        FieldAnnotation2 fieldAnnotation = onField.getAnnotation(FieldAnnotation2.class);

        assertEquals("package-stereotype", fieldAnnotation.value());
    }

    // a test for @Inherited annotations on a field doesn't make a lot of sense,
    // as an inherited field is still the same... you can't override it.
}
