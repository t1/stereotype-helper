# About [ ![Download](https://api.bintray.com/packages/t1/javaee-helpers/stereotype-helper/images/download.png) ](https://bintray.com/t1/javaee-helpers/stereotype-helper/_latestVersion) [![Dependency Status](https://www.versioneye.com/user/projects/53fb94f2e09da317ca000650/badge.svg?style=flat)](https://www.versioneye.com/user/projects/53fb94f2e09da317ca000650) [![Build Status](https://travis-ci.org/t1/stereotype-helper.svg?branch=master)](https://travis-ci.org/t1/stereotype-helper)

Helps libraries with using annotations, supporting propagation (e.g. from class to field) and stereotypes, so their users can encapsulate the libraries' annotations into their own, allowing to abstract and reuse them.

Similar to David Blevins' [MetaTypes](https://github.com/dblevins/metatypes) but this one can propagate annotations.

Start learning the usage at the Annotations class.

# Concepts #

* `Library`: Defines annotations and handles the effect, e.g. JPA.
* `Client Code`: Where annotations are placed to have that effect.
* {@link Stereotype} (careful: this is our own type!): An annotation defined as well as used in client code; allows annotation reuse (i.e. produce less duplication in annotations and work with more consistent and simpler annotations).
* `Resolve`: To turn Stereotypes to normal annotations.
* `Default Annotation`: Annotations e.g. on the class level are effective for all methods or fields in the class.
* `Propagation`: Annotation properties (methods) on the Stereotype are effective on the library-annotations. This works automatically for properties of the same name and type, or can be explicitly configured with {@link PropagetTo} annotations.
* `Inheritance`: Types inherit the annotations of their super class if the annotation itself is annotated as `@Inherited`. This should also be true for all interfaces implemented.

## Ideas ##
* Support annotation processors
* Annotation-indexes
* PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, and PACKAGE annotations
* Injecting annotations and properties from (xml?) config files (deployment descriptors)
* Merge list annotations
