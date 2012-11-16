package com.github.t1.stereotypes;

import static org.junit.Assert.*;

import java.lang.annotation.*;

import org.junit.Test;

public class AnnotationsMethodTest {
    @Test
    public void directAnnotationShouldBePresent() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        boolean present = Annotations.onMethod(Target.class, "method").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test(expected = NullPointerException.class)
    public void nullAnnotationShouldThrowNPE() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        Annotations.onMethod(Target.class, "method").isAnnotationPresent(null);
    }

    @Test
    public void missingAnnotationShouldNotBePresent() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        boolean present = Annotations.onMethod(Target.class, "method").isAnnotationPresent(Retention.class);

        assertFalse(present);
    }

    @Test
    public void stereotypeAnnotationShouldNotBePresent() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        boolean present = Annotations.onMethod(Target.class, "method").isAnnotationPresent(Stereotype.class);

        assertFalse(present);
    }

    @Test
    public void shouldGetDirectAnnotation() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "method").getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation.value());
    }

    @Test
    public void shouldGetDirectAnnotationWithArgs() throws Exception {
        class Target {
            @MethodAnnotation
            public String method(String arg) {
                return arg;
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "method", String.class).getAnnotation(
                MethodAnnotation.class);

        assertEquals("default", annotation.value());
    }

    @Test(expected = NullPointerException.class)
    public void gettingNullAnnotationShouldThrowNPE() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        Annotations.onMethod(Target.class, "method").getAnnotation(null);
    }

    @Test
    public void missingAnnotationShouldGetNull() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        Retention annotation = Annotations.onMethod(Target.class, "method").getAnnotation(Retention.class);

        assertNull(annotation);
    }

    @Test
    public void shouldGetDirectAnnotations() throws Exception {
        class Target {
            @MethodAnnotation
            public String method() {
                return "";
            }
        }

        Annotation[] annotations = Annotations.onMethod(Target.class, "method").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("default", ((MethodAnnotation) annotations[0]).value());
    }

    @Test
    public void expandedAnnotationShouldBePresent() throws Exception {
        class Target {
            @MethodStereotype
            public String method() {
                return "";
            }
        }

        boolean present = Annotations.onMethod(Target.class, "method").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void indirectlyExpandedAnnotationShouldBePresent() throws Exception {
        class Target {
            @IndirectMethodStereotype
            public String method() {
                return "";
            }
        }

        boolean present = Annotations.onMethod(Target.class, "method").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetExpandedAnnotations() throws Exception {
        class Target {
            @MethodStereotype
            public String method() {
                return "";
            }
        }

        Annotation[] annotations = Annotations.onMethod(Target.class, "method").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("stereotype-test", ((MethodAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() throws Exception {
        class Target {
            @IndirectMethodStereotype
            public String method() {
                return "";
            }
        }

        Annotation[] annotations = Annotations.onMethod(Target.class, "method").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("stereotype-test", ((MethodAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() throws Exception {
        class Target {
            @MethodAnnotation("overwritten-test")
            @MethodStereotype
            public String method() {
                return "";
            }
        }

        Annotation[] annotations = Annotations.onMethod(Target.class, "method").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("overwritten-test", ((MethodAnnotation) annotations[0]).value());
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() throws Exception {
        class Target {
            @MethodAnnotation("overwritten-test")
            @IndirectMethodStereotype
            public String method() {
                return "";
            }
        }

        Annotation[] annotations = Annotations.onMethod(Target.class, "method").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("overwritten-test", ((MethodAnnotation) annotations[0]).value());
    }
}
