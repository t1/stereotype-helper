package com.github.t1.stereotypes.test;

import static org.junit.Assert.*;

import java.lang.annotation.*;

import org.junit.Test;

import com.github.t1.stereotypes.*;

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
    public void shouldGetDirectAnnotationWithArg() throws Exception {
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

    @Test
    public void shouldGetDirectAnnotationWithMultipleMethods() throws Exception {
        class Target {
            @MethodAnnotation
            public String method1(String arg) {
                return arg;
            }

            @MethodAnnotation
            public String method2(String arg) {
                return arg;
            }
        }

        MethodAnnotation annotation1 = Annotations.onMethod(Target.class, "method1", String.class).getAnnotation(
                MethodAnnotation.class);

        assertEquals("default", annotation1.value());

        MethodAnnotation annotation2 = Annotations.onMethod(Target.class, "method2", String.class).getAnnotation(
                MethodAnnotation.class);

        assertEquals("default", annotation2.value());
    }

    @Test
    public void shouldGetDirectAnnotationWithMultipleArgs() throws Exception {
        class Target {
            @MethodAnnotation
            public String method(String arg0, int arg1) {
                return arg0 + arg1;
            }
        }

        MethodAnnotation annotation1 = Annotations.onMethod(Target.class, "method", String.class, Integer.TYPE).getAnnotation(
                MethodAnnotation.class);

        assertEquals("default", annotation1.value());
    }

    @Test
    public void shouldGetDirectAnnotationMultipleMethodsWithSameName() throws Exception {
        class Target {
            @MethodAnnotation
            public String method(String arg) {
                return arg;
            }

            @MethodAnnotation
            public Integer method(Integer arg) {
                return arg;
            }
        }

        MethodAnnotation annotation1 = Annotations.onMethod(Target.class, "method", String.class).getAnnotation(
                MethodAnnotation.class);

        assertEquals("default", annotation1.value());

        MethodAnnotation annotation2 = Annotations.onMethod(Target.class, "method", Integer.class).getAnnotation(
                MethodAnnotation.class);

        assertEquals("default", annotation2.value());
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

    @Test
    public void shouldDefaultToTypeAnnotation() throws Exception {
        @MethodAnnotation("type-default")
        class Target {
            @SuppressWarnings("unused")
            public String method() {
                return "";
            }
        }

        Annotation[] annotations = Annotations.onMethod(Target.class, "method").getAnnotations();

        assertEquals(1, annotations.length);
        assertEquals("type-default", ((MethodAnnotation) annotations[0]).value());
    }
}
