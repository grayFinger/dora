package com.dora.common.exception.wrap;

import java.util.List;

import com.dora.common.exception.BusinessException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;


public class MethodArgumentNotValidExceptionWrapper implements IExceptionWrapper<MethodArgumentNotValidException> {
    public MethodArgumentNotValidExceptionWrapper() {
    }

    public Throwable process(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getAllErrors();
        if (errors != null && errors.size() > 0) {
            ObjectError error = (ObjectError)errors.get(0);
            return new BusinessException("7001", error.getDefaultMessage());
        } else {
            return null;
        }
    }
}
