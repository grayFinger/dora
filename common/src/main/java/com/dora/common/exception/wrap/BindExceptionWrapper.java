package com.dora.common.exception.wrap;

import java.util.List;

import com.dora.common.exception.BusinessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

public class BindExceptionWrapper implements IExceptionWrapper<BindException> {
    public BindExceptionWrapper() {
    }

    public Throwable process(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return fieldErrors != null && fieldErrors.size() > 0 ? new BusinessException("7001", ((FieldError)fieldErrors.get(0)).getDefaultMessage()) : null;
    }
}
