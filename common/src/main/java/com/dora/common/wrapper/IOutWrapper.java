package com.dora.common.wrapper;

import com.dora.common.exception.ICodeException;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IOutWrapper {
    default Object wrapperSuccess(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
        return this.wrapper(request, response, "0", "", data);
    }

    default Object wrapperError(HttpServletRequest request, HttpServletResponse response, Throwable e) throws IOException {
        String code = "8000";
        String msg = "系统异常";
        Object data = null;
        if (e instanceof ICodeException) {
            code = ((ICodeException)e).getBusiCode();
            msg = e.getMessage();
            data = ((ICodeException)e).getData();
        }

        return this.wrapper(request, response, code, msg, data);
    }

    default Object wrapper(HttpServletRequest request, HttpServletResponse response, String code, String msg, Object data) throws IOException {
        return data;
    }
}
