package com.dora.common.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dora.common.config.JsonConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class ResponseBodyWrapFactoryBean implements InitializingBean {
    @Autowired
    private RequestMappingHandlerAdapter adapter;
    @Autowired
    private JsonConfig jsonConfig;

    public ResponseBodyWrapFactoryBean() {
    }

    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = this.adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        this.decorateHandlers(handlers);
        this.adapter.setReturnValueHandlers(handlers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        Iterator var2 = handlers.iterator();

        while(var2.hasNext()) {
            HandlerMethodReturnValueHandler handler = (HandlerMethodReturnValueHandler)var2.next();
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                ResponseBodyWrapHandler decorator = new ResponseBodyWrapHandler(handler, this.jsonConfig);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
                break;
            }
        }

    }
}
