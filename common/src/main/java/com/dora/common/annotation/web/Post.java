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
import org.springframework.web.bind.annotation.PostMapping;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Post {
    @AliasFor(
        annotation = PostMapping.class
    )
    String name() default "";

    @AliasFor(
        annotation = PostMapping.class
    )
    String[] value() default {};

    @AliasFor(
        annotation = PostMapping.class
    )
    String[] path() default {};

    @AliasFor(
        annotation = PostMapping.class
    )
    String[] params() default {};

    @AliasFor(
        annotation = PostMapping.class
    )
    String[] headers() default {};

    @AliasFor(
        annotation = PostMapping.class
    )
    String[] consumes() default {};

    @AliasFor(
        annotation = PostMapping.class
    )
    String[] produces() default {};
}
