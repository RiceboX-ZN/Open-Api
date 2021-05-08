package com.school.zuul.filter;

import com.google.inject.internal.cglib.core.$ReflectUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.school.zuul.entity.RequestHandlerContext;
import com.school.zuul.hand.RequestHandlerChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AccessFilter extends ZuulFilter {

    @Autowired
    private RequestHandlerChain requestHandlerChain;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过网关-------------");
        RequestContext currentContext = RequestContext.getCurrentContext();
        RequestHandlerContext handler = requestHandlerChain.handler(new RequestHandlerContext(currentContext));
        //出错 或者 token为空，则返回，不进行路由
        if (handler.isError()) {
            currentContext.setResponseStatusCode(405);
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("not authentication or method not support method");
            currentContext.getResponse().setContentType("application/json;charset=UTF-8");
            log.error("not authentication or method not support");
        } else if (handler.isAllow()) {
            log.info("request ok");
            return null;
        } else if (StringUtils.isEmpty(handler.getAccessToken())) {
            currentContext.setResponseStatusCode(405);
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("not authentication");
            currentContext.getResponse().setContentType("application/json;charset=UTF-8");
            log.error("not authentication or method not support");
        }else {
            log.info("request ok");
        }
        return null;

    }
}
