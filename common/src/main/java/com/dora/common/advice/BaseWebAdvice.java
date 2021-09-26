//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.advice;

import org.aspectj.lang.annotation.Pointcut;

public class BaseWebAdvice extends BaseAdvice {
    public BaseWebAdvice() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappingPointCut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingPointCut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMappingPointCut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMappingPointCut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMappingPointCut() {
    }

    @Pointcut("requestMappingPointCut() || getMappingPointCut() || postMappingPointCut() || deleteMappingPointCut()")
    public void webPointCut() {
    }

    @Pointcut("packagePointCut() && webPointCut()")
    public void packageAndWebPointCut() {
    }
}
