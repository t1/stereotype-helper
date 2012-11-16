package com.example.stereotypes;

import static org.junit.Assert.*;

import java.lang.annotation.*;

import org.junit.Test;

import com.example.stereotypes.*;

public class AnnotationsTest {

    @Test
    public void directAnnotationShouldBePresent() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        boolean present = Annotations.on(Target.class).isAnnotationPresent(TestAnnotation.class);

        assertTrue(present);
    }

    @Test(expected = NullPointerException.class)
    public void nullAnnotationShouldThrowNPE() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        Annotations.on(Target.class).isAnnotationPresent(null);
    }

    @Test
    public void missingAnnotationShouldNotBePresent() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        boolean present = Annotations.on(Target.class).isAnnotationPresent(Retention.class);

        assertFalse(present);
    }

    @Test
    public void stereotypeAnnotationShouldNotBePresent() throws Exception {
        @TestStereotype
        class Target {
        }

        boolean present = Annotations.on(Target.class).isAnnotationPresent(Stereotype.class);

        assertFalse(present);
    }

    @Test
    public void shouldGetDirectAnnotation() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        TestAnnotation annotation = Annotations.on(Target.class).getAnnotation(TestAnnotation.class);

        assertEquals("test", annotation.value());
    }

    @Test(expected = NullPointerException.class)
    public void gettingNullAnnotationShouldThrowNPE() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        Annotations.on(Target.class).getAnnotation(null);
    }

    @Test
    public void missingAnnotationShouldGetNull() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        Retention annotation = Annotations.on(Target.class).getAnnotation(Retention.class);

        assertNull(annotation);
    }

    @Test
    public void shouldGetDirectAnnotations() throws Exception {
        @TestAnnotation("test")
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("test", ((TestAnnotation) annotations[0]).value());
    }

    @Test
    public void inheritedAnnotationShouldBePresent() throws Exception {
        @TestStereotype()
        class Target {
        }

        boolean present = Annotations.on(Target.class).isAnnotationPresent(TestAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void doubleIndirectAnnotationShouldBePresent() throws Exception {
        @IndirectTestStereotype
        class Target {
        }

        boolean present = Annotations.on(Target.class).isAnnotationPresent(TestAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetInheritedAnnotations() throws Exception {
        @TestStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("stereotype-test", ((TestAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() throws Exception {
        @IndirectTestStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("indirect-test", ((TestAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldInheritNoTargetAnnotation() throws Exception {
        @NoTargetStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof NoTargetAnnotation);
    }

    @Test
    public void shouldOverwriteInheritedAnnotationValues() throws Exception {
        @TestAnnotation("overwritten-test")
        @TestStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("overwritten-test", ((TestAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldOverwriteDoubleInheritedAnnotationValues() throws Exception {
        @TestAnnotation("overwritten-test")
        @IndirectTestStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("overwritten-test", ((TestAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldInheritMetaOrTypeStereotype() throws Exception {
        @MetaStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(2, annotations.length);
        int a = (annotations[0] instanceof TestAnnotation) ? 0 : 1;
        int b = (a + 1) % 2;
        assertEquals("meta-test", ((TestAnnotation) annotations[a]).value());
        assertEquals("meta-or-type-annotation", ((MetaOrTypeAnnotation) annotations[b]).value());
    }

    @Test
    public void shouldGetOverwritingDefault() throws Exception {
        @OverwritingStereotype
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("default", ((TestAnnotation) annotations[0]).value());
        assertEquals(3, ((TestAnnotation) annotations[0]).number());
    }

    @Test
    public void shouldGetOverwrittenOverwriting() throws Exception {
        @OverwritingStereotype(value = "passed-in", number = 5)
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("passed-in", ((TestAnnotation) annotations[0]).value());
        assertEquals(5, ((TestAnnotation) annotations[0]).number());
    }

    @Test
    public void shouldNotGetDefaultConflictingReturnTypeOverwrite() throws Exception {
        @OverwritingStereotype2
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals(99, ((TestAnnotation2) annotations[0]).value());
    }

    @Test
    public void shouldNotGetOverwrittenConflictingReturnTypeOverwrite() throws Exception {
        @OverwritingStereotype2(value = "hi", number = 3)
        class Target {
        }

        Annotation[] annotations = Annotations.on(Target.class).getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals(99, ((TestAnnotation2) annotations[0]).value());
    }
}
