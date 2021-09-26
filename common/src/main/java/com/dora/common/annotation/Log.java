package com.dora.common.annotation;


import com.dora.common.log.DefaultLoggerContainer;
import com.dora.common.log.ILoggerHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {
    String label();

    String code() default "";

    String description() default "";

    Class<? extends ILoggerHandler> logHandler() default DefaultLoggerContainer.class;
}