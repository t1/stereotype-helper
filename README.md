# About [![Build Status](https://travis-ci.org/t1/stereotype-helper.svg?branch=master)](https://travis-ci.org/t1/stereotype-helper) [![](https://jitci.com/gh/t1/stereotype-helper/svg)](https://jitci.com/gh/t1/stereotype-helper) [![](https://jitpack.io/v/t1/stereotype-helper.svg)](https://jitpack.io/#t1/stereotype-helper)

Helps libraries with using annotations, supporting defaulting (e.g. from class to field) and stereotypes, so their users can encapsulate the libraries' annotations into their own, allowing to abstract and reuse them.

<small>Similar to David Blevins' [MetaTypes](https://github.com/dblevins/metatypes) but this one can propagate stereotype properties and more. And this one uses the standard CDI `@Stereotype` annotation and assumes that you have can change the annotations you want to check (making things much easier).</small>

The main entry point is the Annotations class.

# Tutorial #

## Setup ##

Add a dependency:

```xml
<dependency>
    <groupId>com.github.t1</groupId>
    <artifactId>stereotype-helper</artifactId>
    <version>${latestVersion}</version>
</dependency>
```

Where `latestVersion` is [![Download](https://api.bintray.com/packages/t1/javaee-helpers/stereotype-helper/images/download.png) ](https://bintray.com/t1/javaee-helpers/stereotype-helper/_latestVersion) (you can also download it from there).

## The Basics: Reflecting on Annotations ##

Say you have an annotated field:

```java
class Target {
    @FieldAnnotation
    public String field;
}
```

Without `stereotype-helper`, your code to check the presence of the annotation would look like this:

```java
Field field = Target.class.getDeclaredField("field");
boolean present = field.isAnnotationPresent(FieldAnnotation.class);

```

With `stereotype-helper`, you'd use the `Annotations` class, instead:

```java
Field field = Target.class.getDeclaredField("field");
boolean present = Annotations.on(field).isAnnotationPresent(FieldAnnotation.class);

```

There's a shortcut for those two lines:

```java
boolean present = Annotations.onField(Target.class, "field")
    .isAnnotationPresent(FieldAnnotation.class);
```

`Annotations` has corresponding methods for methods and classes. They all return the standard Java `java.lang.reflect.AnnotatedElement` objects, so the syntax of using them stays exactly the same, only the entry point is different.

No big deal... so far.

## Resolving Stereotypes ##

[Stereotypes](http://docs.jboss.org/cdi/api/1.2/javax/enterprise/inject/Stereotype.html) are a part of CDI to group annotations into reusable roles. But they mostly work only for CDI and Java EE annotations, as only few other frameworks know how to resolve stereotypes to the annotations they group. `stereotype-helper` does this for your framework (or library or wherever you want to react to the annotations)... as you tell from it's name.

First let's define a stereotype:

```java
@Transactional
@MyAnnotation
@Stereotype
@Target(Method)
@Retention(RUNTIME)
public @interface Action {}
```

`@Transactional` is a Java EE annotation; `@MethodAnnotation` is the annotation we'd like to handle in our own framework. The other annotations define it as a stereotype.

When you use this stereotype like this:

```java
class Type {
    @Action
    public void myMethod() {
        ...
    }
}
```

... then this should be true:

```java
Annotations.onMethod(Type.class, "myMethod").isAnnotationPresent(MyAnnotation.class);
```

I.e. the annotations on the stereotype show up as if they where directly annotated at the target (method in this case).

Note that this resolution is recursive, i.e. it works also for stereotypes that are annotated with other stereotypes.

## Stereotype Property Propagation ##

Say you have an annotation with a property like this:

```java
public @interface Color {
    public String value() default "none";
}
```

... and a stereotype like this:

```java
@Color("red")
@Stereotype
public @interface Red {
    public String value();
}
```

... then calling `...getAnnotation(Color.class).value()` on something annotated as `@Red` will return `red` (which is generally not the case for stereotypes in CDI). Even more important, an annotation `@Red("strawberry")` would turn out as `strawberry`, i.e. the property is propagated from the stereotype to the annotation. One stereotype property can also be propagated to multiple annotations.

For propagation to work, the name as well as the type of the property must match. Alternatively you can explicitly configure it with `PropagetTo`, specifying the target types and property names.

## Defaulting Annotations ##

Annotations on a class are effective for all methods and fields within that class, but can be overriden.

Annotations on a package are effective for all classes, methods, and fields within that package, but can be overriden.

## Annotation Inheritance From Interfaces ##

In standard Java, classes inherit the annotations of their super class if the annotation itself is annotated as `@Inherited`. `stereotype-helper` does that for all _interfaces_ implemented, too.
