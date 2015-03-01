# About [![Dependency Status](https://www.versioneye.com/user/projects/53fb94f2e09da317ca000650/badge.svg?style=flat)](https://www.versioneye.com/user/projects/53fb94f2e09da317ca000650) [![Build Status](https://travis-ci.org/t1/stereotype-helper.svg?branch=master)](https://travis-ci.org/t1/stereotype-helper)

Helps libraries with using annotations, supporting propagation (e.g. from class to field) and stereotypes, so their users can encapsulate the libraries' annotations into their own, allowing to abstract and reuse them.

Similar to David Blevins' [MetaTypes](https://github.com/dblevins/metatypes) but this one can propagate annotations.

The main entry point is the Annotations class.

# Tutorial #

## Setup ##

Add a dependency:

```java
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

`Annotations` has corresponding methods for methods and classes.

No big deal... so far.

## Resolving Stereotypes ##

Stereotypes are a part of CDI to group annotations into reusable roles. But they mostly work only for CDI and Java EE annotations, as only few other frameworks know how to resolve stereotypes to their individual annotations. `stereotype-helper` does this for your framework (or library or whatever), obviously.

First let's define a stereotype:

```java
@Transactional
@MyAnnotation
@Stereotype
@Target(Method)
@Retention(RUNTIME)
public @interface Action {}
```

`@Transactional` is a Java EE annotations. `@MethodAnnotation` is the annotation we'd like to handle in our own framework. The other annotations for defining the stereotype.

Now use this stereotype like this:

```java
class Type {
    @Action
    public void myMethod() {
        ...
    }
}
```

... and this should be true:

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

... then calling `...getAnnotation(Color.class).value()` on something annotated as `@Red` will return `red` (which is generally not the case for stereotypes in CDI). Even more important, an annotation `@Red("strawberry")` would turn out as `strawberry`, i.e. the property is propagated from the stereotype to the annotation. For this to work, the name as well as the type of the property must match, or you can explicitly configure it with {@link PropagetTo}. One stereotype property can also be propagated to multiple annotations.

## Default Annotations ##

Annotations on the class level are effective for all methods and fields in the class.

Annotations on a package are effective for all classes, methods, and fields in the class.

## Annotation Inheritance From Interfaces ##

In Java, classes inherit the annotations of their super class if the annotation itself is annotated as `@Inherited`. `stereotype-helper` does that for all _interfaces_ implemented, too.
