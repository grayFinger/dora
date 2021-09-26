package com.dora.common.exception.wrap;

import com.dora.common.exception.BusinessException;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ConstraintViolationExceptionWrapper implements IExceptionWrapper<ConstraintViolationException> {
    public ConstraintViolationExceptionWrapper() {
    }

    public Throwable process(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations != null && constraintViolations.size() > 0) {
            Iterator var3 = constraintViolations.iterator();
            if (var3.hasNext()) {
                ConstraintViolation<?> constraintViolation = (ConstraintViolation)var3.next();
                return new BusinessException("7001", constraintViolation.getMessage());
            }
        }

        return null;
    }
}
