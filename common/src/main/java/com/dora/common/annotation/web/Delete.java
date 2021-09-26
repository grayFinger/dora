//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.annotation.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.DeleteMapping;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Delete {
    @AliasFor(
        annotation = DeleteMapping.class
    )
    String name() default "";

    @AliasFor(
        annotation = DeleteMapping.class
    )
    String[] value() default {};

    @AliasFor(
        annotation = DeleteMapping.class
    )
    String[] path() default {};

    @AliasFor(
        annotation = DeleteMapping.class
    )
    String[] params() default {};

    @AliasFor(
        annotation = DeleteMapping.class
    )
    String[] headers() default {};

    @AliasFor(
        annotation = DeleteMapping.class
    )
    String[] consumes() default {};

    @AliasFor(
        annotation = DeleteMapping.class
    )
    String[] produces() default {};
}
