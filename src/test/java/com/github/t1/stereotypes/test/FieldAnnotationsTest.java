package com.github.t1.stereotypes.test;

import com.github.t1.stereotypes.Annotations;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public class FieldAnnotationsTest {
    @Test
    public void directAnnotationShouldBePresent() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        boolean present = Annotations.onField(Target.class, "field").isAnnotationPresent(FieldAnnotation.class);

        assertThat(present).isTrue();
    }

    @Test
    public void shouldFailWithUndefinedField() {
        Throwable thrown = catchThrowable(() -> Annotations.onField(Target.class, "undefined"));

        assertThat(thrown).isInstanceOf(RuntimeException.class).hasCauseInstanceOf(NoSuchFieldException.class);
    }

    @Test
    public void directAnnotationShouldBePresentByField() throws ReflectiveOperationException {
        class Target {
            @FieldAnnotation
            public String field;
        }

        Field field = Target.class.getDeclaredField("field");

        boolean present = Annotations.on(field).isAnnotationPresent(FieldAnnotation.class);

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

        assertThat(annotation.value()).isEqualTo("default");
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

        assertThat(annotation).isNull();
    }

    @Test
    public void shouldGetDirectAnnotations() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertThat(annotation.value()).isEqualTo("default");
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

        assertThat(annotations.value()).isEqualTo("stereotype-test");
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() {
        class Target {
            @IndirectFieldStereotype
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertThat(annotation.value()).isEqualTo("stereotype-test");
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() {
        class Target {
            @FieldStereotype
            @FieldAnnotation("overwritten-test")
            public String field;
        }

        FieldAnnotation annotations = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertThat(annotations.value()).isEqualTo("overwritten-test");
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() {
        class Target {
            @IndirectFieldStereotype
            @FieldAnnotation("overwritten-test")
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertThat(annotation.value()).isEqualTo("overwritten-test");
    }

    @Test
    public void shouldDefaultToTypeAnnotation() {
        @FieldAnnotation("type-default")
        class Target {
            @SuppressWarnings("unused")
            public String field;
        }

        FieldAnnotation annotation = Annotations.onField(Target.class, "field").getAnnotation(FieldAnnotation.class);

        assertThat(annotation.value()).isEqualTo("type-default");
    }

    @Test
    public void shouldDefaultAndResolveFromType() {
        @FieldStereotype
        class Target {
            @SuppressWarnings("unused")
            public String foo;
        }

        AnnotatedElement onField = Annotations.onField(Target.class, "foo");

        FieldAnnotation fieldAnnotation = onField.getAnnotation(FieldAnnotation.class);

        assertThat(fieldAnnotation.value()).isEqualTo("stereotype-test");
    }

    @Test
    public void shouldDefaultFromPackage() {
        class Target {
            @SuppressWarnings("unused")
            public String foo;
        }

        AnnotatedElement onField = Annotations.onField(Target.class, "foo");

        FieldAnnotation fieldAnnotation = onField.getAnnotation(FieldAnnotation.class);

        assertThat(fieldAnnotation.value()).isEqualTo("package-field-default");
    }

    @Test
    public void shouldDefaultAndResolveFromPackage() {
        class Target {
            @SuppressWarnings("unused")
            public String foo;
        }

        AnnotatedElement onField = Annotations.onField(Target.class, "foo");

        FieldAnnotation2 fieldAnnotation = onField.getAnnotation(FieldAnnotation2.class);

        assertThat(fieldAnnotation.value()).isEqualTo("package-stereotype");
    }

    // a test for @Inherited annotations on a field doesn't make a lot of sense,
    // as an inherited field is still the same... you can't override it.

    @Test
    public void shouldReturnOnlyDirectAnnotationAsDeclared() {
        class Target {
            @FieldAnnotation
            public String field;
        }

        Annotation[] annotations = Annotations.onField(Target.class, "field").getDeclaredAnnotations();

        assertThat(annotations.length).isEqualTo(1);
        assertThat(((FieldAnnotation) annotations[0]).value()).isEqualTo("default");
    }
}
