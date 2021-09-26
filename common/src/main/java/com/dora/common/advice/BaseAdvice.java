package com.dora.common.advice;

import org.aspectj.lang.annotation.Pointcut;

public class BaseAdvice {
    public BaseAdvice() {
    }

    @Pointcut("execution(* com.chinacreator..*.*(..))")
    public void packagePointCut() {
    }
}
