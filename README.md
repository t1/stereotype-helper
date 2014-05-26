# About #

Helps libraries to support stereotypes, so their users can encapsulate the libraries' annotations into their own, allowing to reuse them.

Similar to David Blevins' [MetaTypes](https://github.com/dblevins/metatypes) but this one can propagate annotations.

Start at the Annotations class.

# Concepts #

## Stereotypes ##



## Propagation ##

## Inheritance ##

* Types inherit the annotations of their super class if the annotation itself is annotated as `@Inherited`. This should also be true for all interfaces implemented.

# TODOs #
* Support annotation processors


	Helps libraries to support stereotypes, so their users can encapsulate
	the libraries' annotations into their own.

	<h3>Terms &amp; Features</h3>
	<ul>
		<li><code>Library</code>: Defines annotations and handles the
			effect, e.g. JPA.
		<li><code>Client Code</code>: Where annotations are placed to
			have that effect.
		<li>{@link Stereotype} (careful: this is our own type!): An
			annotation defined as well as used in client code; allows annotation
			reuse (i.e. produce less duplication in annotations and work with
			more consistent and simpler annotations).
		<li><code>Resolve</code>: To turn Stereotypes to normal
			annotations.
		<li><code>Default Annotation</code>: Annotations e.g. on the
			class level are effective for all methods or fields in the class.
		<li><code>Propagation</code>: Annotation properties (methods) on
			the Stereotype are effective on the library-annotations. This works
			automatically for properties of the same name and type, or can be
			explicitly configured with {@link PropagetTo} annotations.
	</ul>

	<h3>Planned Features</h3>
	<ul>
		<li>Annotation-indexes
		<li>PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, and
			PACKAGE annotations
		<li>Allow adding library-annotations and properties from (xml?)
			config files (deployment descriptors)
		<li>Merge list annotations
	</ul>
