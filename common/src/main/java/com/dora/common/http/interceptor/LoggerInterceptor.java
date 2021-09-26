package com.dora.common.http.interceptor;

import com.dora.common.annotation.Log;
import com.dora.common.config.AppConfig;
import com.dora.common.exception.ICodeException;
import com.dora.common.http.HttpRequest;
import com.dora.common.http.HttpResponse;
import com.dora.common.http.IHttpClientResolver;
import com.dora.common.http.IHttpInterceptor;
import com.dora.common.log.LogBean;
import com.dora.common.log.LogService;
import com.dora.common.log.LogVariable;
import com.dora.common.utils.ExceptionUtils;
import com.dora.common.utils.StringMap;

public class LoggerInterceptor implements IHttpInterceptor {
    private static final String LOG_START_TIME = "LOG_START_TIME";
    private LogService logService;
    private AppConfig appConfig;

    public LoggerInterceptor() {
    }

    public boolean preHandler(IHttpClientResolver resolver, HttpRequest request) {
        request.getAttrs().put("LOG_START_TIME", System.currentTimeMillis());
        return true;
    }

    public void postHandler(IHttpClientResolver resolver, HttpRequest request, HttpResponse response, Throwable throwable) {
        if (this.logService != null) {
            Long start = request.getAttrs().getLong("LOG_START_TIME");
            if (start == null) {
                start = System.currentTimeMillis();
            }

            LogBean logBean = new LogBean();
            logBean.setUrl(request.url());
            logBean.setIp(this.appConfig.getHost());
            logBean.setPort(this.appConfig.getPort());
            logBean.setPlatform("interface");
            logBean.setMethodType(request.method().getName());
            logBean.setIp((String) LogVariable.get("ip"));
            logBean.setTraceID((String)LogVariable.get("traceId"));
            logBean.setParams(request.params());
            logBean.setHeaders(request.headers());
            logBean.setBody(request.body());
            logBean.setTime(System.currentTimeMillis() - start);
            if (response != null) {
                logBean.setResult(response.getResult());
                if (!response.isSuccess()) {
                    logBean.setCode(response.getCode() + "");
                    logBean.setMessage(response.getMessage());
                }
            }

            if (throwable != null) {
                Throwable t = ExceptionUtils.getCause(throwable);
                if (t instanceof ICodeException) {
                    logBean.setCode(((ICodeException)t).getBusiCode());
                } else {
                    logBean.setCode("8000");
                }

                logBean.setMessage(t.getMessage());
            }

            this.logService.log(logBean, (Log)null, (StringMap)null);
        }

    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
