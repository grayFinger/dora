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
import org.springframework.web.bind.annotation.GetMapping;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Get {
    @AliasFor(
        annotation = GetMapping.class
    )
    String name() default "";

    @AliasFor(
        annotation = GetMapping.class
    )
    String[] value() default {};

    @AliasFor(
        annotation = GetMapping.class
    )
    String[] path() default {};

    @AliasFor(
        annotation = GetMapping.class
    )
    String[] params() default {};

    @AliasFor(
        annotation = GetMapping.class
    )
    String[] headers() default {};

    @AliasFor(
        annotation = GetMapping.class
    )
    String[] consumes() default {};

    @AliasFor(
        annotation = GetMapping.class
    )
    String[] produces() default {};
}
