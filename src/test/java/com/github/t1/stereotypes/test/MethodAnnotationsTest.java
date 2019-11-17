package com.github.t1.stereotypes.test;

import com.github.t1.stereotypes.Annotations;
import org.junit.Ignore;
import org.junit.Test;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public class MethodAnnotationsTest {
    @Test
    public void directAnnotationShouldBePresent() throws NoSuchMethodException {
        class Target {
            @SuppressWarnings("WeakerAccess")
            @MethodAnnotation
            public void foo() {
            }
        }
        Method method = Target.class.getMethod("foo");

        boolean present = Annotations.on(method).isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void shouldFailWithUndefinedField() {
        Throwable thrown = catchThrowable(() -> Annotations.onMethod(Target.class, "undefined"));

        assertThat(thrown).isInstanceOf(RuntimeException.class).hasCauseInstanceOf(NoSuchMethodException.class);
    }

    @Test
    public void directAnnotationOnMethodShouldBePresent() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test(expected = NullPointerException.class)
    public void nullAnnotationShouldThrowNPE() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        Annotations.onMethod(Target.class, "foo").isAnnotationPresent(null);
    }

    @Test
    public void missingAnnotationShouldNotBePresent() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(Retention.class);

        assertFalse(present);
    }

    @Test
    public void stereotypeAnnotationShouldNotBePresent() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(Stereotype.class);

        assertFalse(present);
    }

    @Test
    public void shouldGetDirectAnnotation() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("default");
    }

    @Test
    public void shouldGetDirectAnnotationWithArg() {
        class Target {
            @MethodAnnotation
            public void foo(String arg) {
            }
        }

        MethodAnnotation annotation =
            Annotations.onMethod(Target.class, "foo", String.class).getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("default");
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

        assertThat(annotation1.value()).isEqualTo("default");

        MethodAnnotation annotation2 =
            Annotations.onMethod(Target.class, "method2", String.class).getAnnotation(MethodAnnotation.class);

        assertThat(annotation2.value()).isEqualTo("default");
    }

    @Test
    public void shouldGetDirectAnnotationWithMultipleArgs() {
        class Target {
            @MethodAnnotation
            public String foo(String arg0, int arg1) {
                return arg0 + arg1;
            }
        }

        MethodAnnotation annotation1 = Annotations.onMethod(Target.class, "foo", String.class, Integer.TYPE)
            .getAnnotation(MethodAnnotation.class);

        assertThat(annotation1.value()).isEqualTo("default");
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

        assertThat(annotation1.value()).isEqualTo("default");

        MethodAnnotation annotation2 =
            Annotations.onMethod(Target.class, "foo", Integer.class).getAnnotation(MethodAnnotation.class);

        assertThat(annotation2.value()).isEqualTo("default");
    }

    @Test(expected = NullPointerException.class)
    public void gettingNullAnnotationShouldThrowNPE() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        Annotations.onMethod(Target.class, "foo").getAnnotation(null);
    }

    @Test
    public void missingAnnotationShouldGetNull() {
        class Target {
            @MethodAnnotation
            public void foo() {
            }
        }

        Retention annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(Retention.class);

        assertThat(annotation).isNull();
    }

    @Test
    public void expandedAnnotationShouldBePresent() {
        class Target {
            @MethodStereotype
            public void foo() {
            }
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void indirectlyExpandedAnnotationShouldBePresent() {
        class Target {
            @IndirectMethodStereotype
            public void foo() {
            }
        }

        boolean present = Annotations.onMethod(Target.class, "foo").isAnnotationPresent(MethodAnnotation.class);

        assertTrue(present);
    }

    @Test
    public void shouldGetStereotypeAnnotation() {
        class Target {
            @MethodStereotype
            public void foo() {
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("stereotype-test");
    }

    @Test
    public void shouldGetDoubleIndirectAnnotations() {
        class Target {
            @IndirectMethodStereotype
            public void foo() {
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("stereotype-test");
    }

    @Test
    public void shouldOverwriteExpandedAnnotationValues() {
        class Target {
            @MethodStereotype
            @MethodAnnotation("overwritten-test")
            public void foo() {
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("overwritten-test");
    }

    @Test
    public void shouldOverwriteDoubleExpandedAnnotationValues() {
        class Target {
            @IndirectMethodStereotype
            @MethodAnnotation("overwritten-test")
            public void foo() {
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("overwritten-test");
    }

    @Test
    public void shouldDefaultToTypeAnnotation() {
        @MethodAnnotation("type-default")
        class Target {
            public void foo() {
            }
        }

        MethodAnnotation annotation = Annotations.onMethod(Target.class, "foo").getAnnotation(MethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("type-default");
    }

    @Test
    public void shouldInheritMethodAnnotationFromPackage() {
        class Target {
            public void foo() {
            }
        }

        AnnotatedElement onMethod = Annotations.onMethod(Target.class, "foo");

        MethodAnnotation methodAnnotation = onMethod.getAnnotation(MethodAnnotation.class);

        assertThat(methodAnnotation.value()).isEqualTo("package-method-default");
    }

    @Test
    public void shouldInheritMethodAnnotationFromTypeStereotype() {
        @MethodStereotype
        class Target {
            public void foo() {
            }
        }

        AnnotatedElement onMethod = Annotations.onMethod(Target.class, "foo");

        MethodAnnotation methodAnnotation = onMethod.getAnnotation(MethodAnnotation.class);

        assertThat(methodAnnotation.value()).isEqualTo("stereotype-test");
    }

    @Test
    public void shouldInheritMethodAnnotationFromPackageStereotype() {
        class Target {
            public void foo() {
            }
        }

        AnnotatedElement onMethod = Annotations.onMethod(Target.class, "foo");

        MethodAnnotation2 methodAnnotation = onMethod.getAnnotation(MethodAnnotation2.class);

        assertThat(methodAnnotation.value()).isEqualTo("package-stereotype");
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    private @interface NonInheritedMethodAnnotation {
        String value();
    }

    @Test
    @Ignore("not implemented, yet")
    public void shouldNotInheritNonInheritedMethodAnnotation() {
        class Super {
            @NonInheritedMethodAnnotation("super-method")
            public void foo() {
            }
        }
        class Sub extends Super {}

        NonInheritedMethodAnnotation annotation =
            Annotations.onMethod(Sub.class, "foo").getAnnotation(NonInheritedMethodAnnotation.class);

        assertThat(annotation).isNull();
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    @Inherited
    private @interface InheritedMethodAnnotation {
        String value();
    }

    @Test
    public void shouldInheritMethodAnnotation() {
        class Super {
            @InheritedMethodAnnotation("super-method")
            public void foo() {
            }
        }
        class Sub extends Super {}

        InheritedMethodAnnotation annotation =
            Annotations.onMethod(Sub.class, "foo").getAnnotation(InheritedMethodAnnotation.class);

        assertThat(annotation.value()).isEqualTo("super-method");
    }

    @Test
    @Ignore("not implemented, yet")
    public void shouldInheritOverriddenMethodAnnotation() {
        class Super {
            @InheritedMethodAnnotation("super-method")
            public void foo() {
            }
        }
        class Sub extends Super {
            @Override
            public void foo() {
            }
        }

        InheritedMethodAnnotation annotation =
            Annotations.onMethod(Sub.class, "foo").getAnnotation(InheritedMethodAnnotation.class);

        assertEquals("super-method", annotation.value());
    }
}
