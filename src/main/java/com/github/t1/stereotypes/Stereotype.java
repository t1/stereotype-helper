package com.github.t1.stereotypes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

/**
 * Stereotypes allow you to group a set of annotations into a single annotation describing a more abstract concept. E.g.
 * you can define a stereotype for your beans that provide a remotely accessible service and call it
 * <code>@Service</code>. You can then add all annotations you want for your services to this stereotype, e.g.
 * annotations for remote EJBs, REST, SOAP, etc.
 * 
 * @author Rüdiger zu Dohna
 */
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface Stereotype {
}
