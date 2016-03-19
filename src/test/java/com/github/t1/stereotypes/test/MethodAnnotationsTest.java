package com.github.t1.stereotypes.test;

import com.github.t1.stereotypes.Annotations;
import org.junit.*;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.*;
import java.lang.reflect.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import static org.junit.Assert.*;

@SuppressWarnings("unused")
public class MethodAnnotationsTest {
    @Test
    public void directAnnotationShouldBePresent() throws NoSuchMethodException {
        class Target {
            @SuppressWarnings("WeakerAccess") @MethodAnnotation
            public void foo() {}
        }
        Method method = Target.class.getMethod("foo");

        boolean present = Annotations.on(method).isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void directAnnotationOnMethodShouldBePresent() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test(expected = NullPointerException.class)
    public void nullAnnotationShouldThrowNPE() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        Annotations.onMethod(Target.class, "foo").isAnnotationPresent(null);
    }

    @Test
    public void missingAnnotationShouldNotBePresent() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(Retention.class);

        assertFalse(present);
    }

    @Test
    public void stereotypeAnnotationShouldNotBePresent() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(Stereotype.class);

        assertFalse(present);
    }

    @Test
    public void shouldGetDirectAnnotation() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation.value());
    }

    @Test
    public void shouldGetDirectAnnotationWithArg() {
        class Target {
            @MethodAnnotation
            public void foo(String arg) {}
        }

        MethodAnnotation annotation =
                Annotations.onMethod(Target.class, "foo", String.class).getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation.value());
    }

    @Test
    public void shouldGetDirectAnnotationWithMultipleMethods() {
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

        MethodAnnotation annotation1 =
                Annotations.onMethod(Target.class, "method1", String.class).getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation1.value());

        MethodAnnotation annotation2 =
                Annotations.onMethod(Target.class, "method2", String.class).getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation2.value());
    }

    @Test
    public void shouldGetDirectAnnotationWithMultipleArgs() {
        class Target {
            @MethodAnnotation
            public String foo(String arg0, int arg1) {
                return arg0 + arg1;
            }
        }

        MethodAnnotation annotation1 =
                Annotations.onMethod(Target.class, "foo", String.class, Integer.TYPE).getAnnotation(
                        MethodAnnotation.class);

        assertEquals("default", annotation1.value());
    }

    @Test
    public void shouldGetDirectAnnotationMultipleMethodsWithSameName() {
        class Target {
            @MethodAnnotation
            public String foo(String arg) {
                return arg;
            }

            @MethodAnnotation
            public Integer foo(Integer arg) {
                return arg;
            }
        }

        MethodAnnotation annotation1 =
                Annotations.onMethod(Target.class, "foo", String.class).getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation1.value());

        MethodAnnotation annotation2 =
                Annotations.onMethod(Target.class, "foo", Integer.class).getAnnotation(MethodAnnotation.class);

        assertEquals("default", annotation2.value());
    }

    @Test(expected = NullPointerException.class)
    public void gettingNullAnnotationShouldThrowNPE() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        Annotations.onMethod(Target.class, "foo").getAnnotation(null);
    }

    @Test
    public void missingAnnotationShouldGetNull() {
        class Target {
            @MethodAnnotation
            public void foo() {}
        }

        Retention annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(Retention.class);

        assertNull(annotation);
    }

    @Test
    public void expandedAnnotationShouldBePresent() {
        class Target {
            @MethodStereotype
            public void foo() {}
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void indirectlyExpandedAnnotationShouldBePresent() {
        class Target {
            @IndirectMethodStereotype
            public void foo() {}
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetStereotypeAnnotation() {
        class Target {
            @MethodStereotype
            public void foo() {}
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertEquals("stereotype-test", annotation.value());
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() {
        class Target {
            @IndirectMethodStereotype
            public void foo() {}
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertEquals("stereotype-test", annotation.value());
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() {
        class Target {
            @MethodStereotype
            @MethodAnnotation("overwritten-test")
            public void foo() {}
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertEquals("overwritten-test", annotation.value());
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() {
        class Target {
            @IndirectMethodStereotype
            @MethodAnnotation("overwritten-test")
            public void foo() {}
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertEquals("overwritten-test", annotation.value());
    }

    @Test
    public void shouldDefaultToTypeAnnotation() {
        @MethodAnnotation("type-default")
        class Target {
            public void foo() {}
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertEquals("type-default", annotation.value());
    }

    @Test
    public void shouldInheritMethodAnnotationFromPackage() {
        class Target {
            public void foo() {}
        }

        AnnotatedElement onMethod = Annotations.onMethod(Target.class, "foo");

        MethodAnnotation methodAnnotation = onMethod.getAnnotation(MethodAnnotation.class);

        assertEquals("package-method-default", methodAnnotation.value());
    }

    @Test
    public void shouldInheritMethodAnnotationFromTypeStereotype() {
        @MethodStereotype
        class Target {
            public void foo() {}
        }

        AnnotatedElement onMethod = Annotations.onMethod(Target.class, "foo");

        MethodAnnotation methodAnnotation = onMethod.getAnnotation(MethodAnnotation.class);

        assertEquals("stereotype-test", methodAnnotation.value());
    }

    @Test
    public void shouldInheritMethodAnnotationFromPackageStereotype() {
        class Target {
            public void foo() {}
        }

        AnnotatedElement onMethod = Annotations.onMethod(Target.class, "foo");

        MethodAnnotation2 methodAnnotation = onMethod.getAnnotation(MethodAnnotation2.class);

        assertEquals("package-stereotype", methodAnnotation.value());
    }

    @Retention(RUNTIME)
    @Target({ METHOD })
    private @interface NonInheritedMethodAnnotation {
        String value();
    }

    @Test
    @Ignore("not implemented, yet")
    public void shouldNotInheritNonInheritedMethodAnnotation() {
        class Super {
            @NonInheritedMethodAnnotation("super-method")
            public void foo() {}
        }
        class Sub extends Super {}

        NonInheritedMethodAnnotation annotation =
                Annotations.onMethod(Sub.class, "foo").getAnnotation(NonInheritedMethodAnnotation.class);

        assertNull(annotation);
    }

    @Retention(RUNTIME)
    @Target({ METHOD })
    @Inherited
    private @interface InheritedMethodAnnotation {
        String value();
    }

    @Test
    public void shouldInheritMethodAnnotation() {
        class Super {
            @InheritedMethodAnnotation("super-method")
            public void foo() {}
        }
        class Sub extends Super {}

        InheritedMethodAnnotation annotation =
                Annotations.onMethod(Sub.class, "foo").getAnnotation(InheritedMethodAnnotation.class);

        assertEquals("super-method", annotation.value());
    }

    @Test
    @Ignore("not implemented, yet")
    public void shouldInheritOverriddenMethodAnnotation() {
        class Super {
            @InheritedMethodAnnotation("super-method")
            public void foo() {}
        }
        class Sub extends Super {
            @Override
            public void foo() {}
        }

        InheritedMethodAnnotation annotation =
                Annotations.onMethod(Sub.class, "foo").getAnnotation(InheritedMethodAnnotation.class);

        assertEquals("super-method", annotation.value());
    }
}
