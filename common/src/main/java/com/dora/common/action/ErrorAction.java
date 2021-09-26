package com.dora.common.action;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dora.common.exception.BusinessException;
import com.dora.common.exception.ICodeException;
import com.dora.common.exception.wrap.ExceptionWrapper;
import com.dora.common.utils.ExceptionUtils;
import com.dora.common.wrapper.OutWrapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class ErrorAction implements ErrorController {
    private static Logger logger = LoggerFactory.getLogger(ErrorAction.class);
    @Autowired
    private ErrorAttributes errorAttributes;
    @Autowired
    private OutWrapperService outWrapper;
    @Autowired
    private ExceptionWrapper exceptionWrapper;

    public ErrorAction() {
    }

    @RequestMapping({"/error"})
    public Object error(WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.handlerError(webRequest, request, response,ErrorAttributeOptions.defaults());
    }





    public String getErrorPath() {
        return "/error";
    }

    private HttpStatus getStatus(WebRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code", 0);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    private Object handlerError(WebRequest webRequest, HttpServletRequest request, HttpServletResponse response, ErrorAttributeOptions includeStackTrace) throws IOException {
        Throwable e = this.errorAttributes.getError(webRequest);
        Map<String, Object> data = this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
        String path = (String)data.getOrDefault("path", (Object)null);
        if (path == null) {
            path = (String)request.getAttribute("javax.servlet.forward.request_uri");
        }

        String method = request.getMethod();
        HttpStatus status = this.getStatus(webRequest);
        if (status == HttpStatus.BAD_REQUEST) {
            logger.warn("请求接口[{}][{}]参数无效", path, method);
            e = new BusinessException("7001", "接口参数无效");
        } else if (status == HttpStatus.NOT_FOUND) {
            logger.warn("请求接口[{}][{}]不存在或者参数无效", path, method);
            e = new BusinessException("8002", "请求接口不存在或者参数不正确");
        } else if (e != null) {
            e = this.exceptionWrapper.process(ExceptionUtils.getCause((Throwable)e));
            if (e instanceof ICodeException) {
                logger.warn("请求接口[{}][{}]出现异常，错误编码：{}，异常描述：{}", new Object[]{path, method, ((ICodeException)e).getBusiCode(), ((Throwable)e).getMessage()});
            } else {
                logger.error("请求接口[{}][{}]出现异常：{}", new Object[]{path, method, ((Throwable)e).getMessage(), e});
            }
        }

        request.setAttribute("HAS_WRAPPERED", true);
        return this.outWrapper.wrapperError(request, response, (Throwable)e);
    }
}
