package com.dora.common.advice;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dora.common.exception.BusinessException;
import com.dora.common.exception.ICodeException;
import com.dora.common.exception.wrap.ExceptionWrapper;
import com.dora.common.utils.ExceptionUtils;
import com.dora.common.utils.JsonUtils;
import com.dora.common.wrapper.OutWrapperService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Order(1)
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private static Logger logger = LoggerFactory.getLogger(ResponseAdvice.class);
    @Autowired
    private OutWrapperService outWrapper;
    @Autowired(
        required = false
    )
    private HttpServletRequest request;
    @Autowired(
        required = false
    )
    private HttpServletResponse response;
    @Autowired
    private ExceptionWrapper exceptionWrapper;

    public ResponseAdvice() {
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public Object catchException(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        String path = request.getRequestURI();
        if (StringUtils.isNotEmpty(request.getQueryString())) {
            path = path + "?" + request.getQueryString();
        }

        Throwable t = this.exceptionWrapper.process(ExceptionUtils.getCause(ex));
        String method = request.getMethod();
        if (t instanceof ICodeException && !"8000".equals(((ICodeException)t).getBusiCode())) {
            logger.warn("请求接口[{}][{}][失败]，错误编码：{}， 异常描述：{}", new Object[]{path, method, ((ICodeException)t).getBusiCode(), ((Throwable)t).getMessage()});
        } else if (t instanceof HttpRequestMethodNotSupportedException) {
            t = new BusinessException("8003", "请求接口不支持[" + request.getMethod() + "]操作");
            logger.warn("请求接口[{}][{}][失败]，错误编码：8003， 异常描述：{}", new Object[]{path, method, ((Throwable)t).getMessage()});
        } else if (t instanceof HttpMessageNotReadableException) {
            logger.warn("请求接口[{}][{}][失败]，错误编码：8004， 异常描述：{}", new Object[]{path, method, ((Throwable)t).getMessage()});
            t = new BusinessException("8004", "接口内容不能为空");
        } else if (t instanceof MaxUploadSizeExceededException) {
            t = new BusinessException("8005", "上传文件不能超过：" + ((MaxUploadSizeExceededException)t).getMaxUploadSize());
            logger.warn("请求接口[{}][{}][失败]，错误编码：8005， 异常描述：{}", new Object[]{path, method, ((Throwable)t).getMessage()});
        } else {
            t = new BusinessException("8000", "系统异常");
            logger.error("请求接口[{}][{}][失败]，异常描述：{}", new Object[]{path, method, ex.getMessage(), ex});
        }

        request.setAttribute("HAS_WRAPPERED", true);
        return this.outWrapper.wrapperError(request, response, (Throwable)t);
    }

    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(ResponseBody.class) || AnnotationUtils.findAnnotation(methodParameter.getDeclaringClass(), RestController.class) != null || AnnotationUtils.findAnnotation(methodParameter.getDeclaringClass(), ResponseBody.class) != null;
    }

    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        Object ret = null;
        if (object != null) {
            if (object instanceof Resource) {
                ret = object;
            } else {
                ret = this.wrapper(object);
                if (ret == null) {
                    ret = object;
                }

                if (object instanceof String && !(ret instanceof String)) {
                    ret = JsonUtils.toJson(ret);
                }
            }
        }

        return ret;
    }

    private Object wrapper(Object data) {
        Object wrapped = this.request.getAttribute("HAS_WRAPPERED");
        if (wrapped == null) {
            try {
                return this.outWrapper.wrapperSuccess(this.request, this.response, data);
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

        this.request.removeAttribute("HAS_WRAPPERED");
        return data;
    }
}
