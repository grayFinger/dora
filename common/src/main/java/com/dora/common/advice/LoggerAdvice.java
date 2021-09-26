package com.dora.common.advice;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletRequest;

import com.dora.common.annotation.Log;
import com.dora.common.annotation.LogModule;
import com.dora.common.exception.ICodeException;
import com.dora.common.log.LogBean;
import com.dora.common.log.LogService;
import com.dora.common.log.LogVariable;
import com.dora.common.utils.ExceptionUtils;
import com.dora.common.utils.JoinPointUtils;
import com.dora.common.utils.RequestUtils;
import com.dora.common.utils.StringMap;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Order(1)
public class LoggerAdvice extends BaseWebAdvice {
    private static Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);
    @Autowired(
        required = false
    )
    private HttpServletRequest request;
    private LogService logService;

    public LoggerAdvice() {
    }

    @Around("webPointCut()")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        LogBean logBean = new LogBean();
        String traceId = (String)LogVariable.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
            LogVariable.put("traceId", traceId);
        }

        logBean.setTraceID(traceId);
        String url = null;
        if (this.request != null) {
            url = this.request.getRequestURI();
            if ("/error".equals(url)) {
                url = (String)this.request.getAttribute("javax.servlet.forward.request_uri");
            }

            logBean.setIp(RequestUtils.getRealIP(this.request));
            logBean.setMethodType(this.request.getMethod());
            logBean.setSession(this.request.getSession());
        }

        LogVariable.put("ip", logBean.getIp());
        logBean.setClazz(point.getTarget().getClass());
        logBean.setUrl(url);
        logBean.setPlatform("web");
        Object result = null;
        Object logResult = null;
        Long start = System.currentTimeMillis();
        boolean var19 = false;

        try {
            var19 = true;
            result = point.proceed();
            logResult = result;
            if (result != null) {
                if (result instanceof ModelAndView) {
                    logResult = ((ModelAndView)result).getModelMap();
                    var19 = false;
                } else if (result instanceof ResponseEntity) {
                    logResult = null;
                    var19 = false;
                } else if (result instanceof Resource) {
                    logResult = null;
                    var19 = false;
                } else if (result instanceof Future) {
                    logResult = ((Future)result).get();
                    var19 = false;
                } else if (result instanceof DeferredResult) {
                    logResult = ((DeferredResult)result).getResult();
                    var19 = false;
                } else {
                    var19 = false;
                }
            } else {
                var19 = false;
            }
        } catch (Throwable var20) {
            Throwable t = ExceptionUtils.getCause(var20);
            String code = "8000";
            if (t instanceof ICodeException) {
                code = ((ICodeException)t).getBusiCode();
                logBean.setResult(((ICodeException)t).getData());
            }

            logBean.setMessage(t.getMessage());
            logBean.setCode(code);
            logBean.setThrowable(var20);
            throw t;
        } finally {
            if (var19) {
                if (this.logService != null) {
                    logBean.setResult(logResult);
                    logBean.setTime(System.currentTimeMillis() - start);
                    StringMap headers = this.getHeaders(this.request);
                    StringMap params = this.getParams(this.request);
                    logBean.setHeaders(headers);
                    logBean.setParams(params);
                    Log log = null;
                    MethodSignature method = this.getMethod(point);
                    if (method != null) {
                        logBean.setMethod(method.getMethod());
                        logBean.setMethodName(method.getMethod().getName());
                        logBean.setBody(this.getBody(method.getMethod(), point));
                        log = this.processLog(point, method, params, logBean, point.getArgs());
                    }

                    this.logService.log(logBean, log, LogVariable.get());
                }

            }
        }

        if (this.logService != null) {
            logBean.setResult(logResult);
            logBean.setTime(System.currentTimeMillis() - start);
            StringMap headers = this.getHeaders(this.request);
            StringMap params = this.getParams(this.request);
            logBean.setHeaders(headers);
            logBean.setParams(params);
            Log log = null;
            MethodSignature method = this.getMethod(point);
            if (method != null) {
                logBean.setMethod(method.getMethod());
                logBean.setMethodName(method.getMethod().getName());
                logBean.setBody(this.getBody(method.getMethod(), point));
                log = this.processLog(point, method, params, logBean, point.getArgs());
            }

            this.logService.log(logBean, log, LogVariable.get());
        }

        return result;
    }

    private Log processLog(ProceedingJoinPoint point, MethodSignature methodSignature, StringMap params, LogBean logBean, Object[] args) {
        Log log = this.getLogAnnotation(point);
        if (log != null) {
            logBean.setLabel(log.label());
            logBean.setBusiCode(log.code());
            logBean.setDescription(log.description());
            if (this.isSpelExpression(log.description()) || this.isSpelExpression(log.label())) {
                EvaluationContext ctx = new StandardEvaluationContext();
                LogModule module = (LogModule) JoinPointUtils.getAnnotationByClass(point, LogModule.class);
                if (module == null) {
                    ctx.setVariable("module", "");
                } else {
                    ctx.setVariable("module", module.value());
                    logBean.setModule(module.value());
                }

                if (params != null) {
                    Iterator var9 = params.keySet().iterator();

                    while(var9.hasNext()) {
                        String key = (String)var9.next();
                        ctx.setVariable(key, params.get(key));
                    }
                }

                String[] methodParams = methodSignature.getParameterNames();
                if (methodParams != null && methodParams.length > 0) {
                    int index = 0;
                    String[] var11 = methodParams;
                    int var12 = methodParams.length;

                    for(int var13 = 0; var13 < var12; ++var13) {
                        String methodParam = var11[var13];
                        ctx.setVariable(methodParam, args[index]);
                        ++index;
                    }
                }

                StringMap variables = LogVariable.get();
                String description;
                if (variables != null && variables.size() > 0) {
                    Iterator var19 = variables.keySet().iterator();

                    while(var19.hasNext()) {
                        description = (String)var19.next();
                        ctx.setVariable(description, variables.get(description));
                    }
                }

                ctx.setVariable("_", logBean.getResult());
                SpelExpressionParser parser = new SpelExpressionParser();

                try {
                    if (this.isSpelExpression(log.description())) {
                        description = (String)parser.parseExpression(log.description()).getValue(ctx, String.class);
                        logBean.setDescription(description);
                    }

                    if (this.isSpelExpression(log.label())) {
                        logBean.setLabel((String)parser.parseExpression(log.label()).getValue(ctx, String.class));
                    }
                } catch (Throwable var15) {
                    logger.error("日志业务描述不是正确的SPEL表达式，解析失败，业务描述为：{}", log.description(), var15);
                    logBean.setDescription(log.description());
                }
            }
        }

        return log;
    }

    private boolean isSpelExpression(String description) {
        return StringUtils.isNotEmpty(description) && (description.startsWith("'") || description.startsWith("#"));
    }

    private Log getLogAnnotation(ProceedingJoinPoint point) {
        return (Log)JoinPointUtils.getAnnotationByMethod(point, Log.class);
    }

    private MethodSignature getMethod(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        return signature instanceof MethodSignature ? (MethodSignature)signature : null;
    }

    private Object getBody(Method method, ProceedingJoinPoint point) {
        Parameter[] parameters = method.getParameters();
        int index = -1;
        int bodyIndex = -1;
        if (parameters != null && parameters.length > 0) {
            Parameter[] var6 = parameters;
            int var7 = parameters.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Parameter parameter = var6[var8];
                ++index;
                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    bodyIndex = index;
                    break;
                }
            }
        }

        return bodyIndex != -1 ? point.getArgs()[index] : null;
    }

    private StringMap getParams(HttpServletRequest request) {
        StringMap map = new StringMap();
        Enumeration params = request.getParameterNames();

        while(params.hasMoreElements()) {
            String name = (String)params.nextElement();
            String value = request.getParameter(name);
            if (StringUtils.isNotEmpty(value)) {
                map.put(name, value);
            }
        }

        return map;
    }

    private StringMap getHeaders(HttpServletRequest request) {
        StringMap map = new StringMap();
        Enumeration params = request.getHeaderNames();

        while(params.hasMoreElements()) {
            String name = (String)params.nextElement();
            String value = request.getHeader(name);
            if (StringUtils.isNotEmpty(value)) {
                map.put(name, value);
            }
        }

        return map;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
}
