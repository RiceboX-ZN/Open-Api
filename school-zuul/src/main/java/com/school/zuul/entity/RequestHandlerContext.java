package com.school.zuul.entity;

import com.netflix.zuul.context.RequestContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Data
@Slf4j
public class RequestHandlerContext {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PARAMETER_ACCESS_TOKEN = "access_token";
    private static final String PREFIX_BEARER = "bearer";

    //constructor
    private RequestContext requestContext;
    private HttpServletRequest httpServletRequest;
    private String uri;
    private RequestMethod method;
    private String accessToken;

    //allow
    private boolean allow;
    //error
    private boolean error;

    public RequestHandlerContext(RequestContext requestContext) {
        this.requestContext = requestContext;
        if (requestContext != null) {
            this.httpServletRequest = requestContext.getRequest();
            if (httpServletRequest != null) {
                this.uri = httpServletRequest.getRequestURI();
                try {
                    this.method = RequestMethod.valueOf(httpServletRequest.getMethod().toUpperCase());
                } catch (IllegalArgumentException e) {
                    log.error("not support this request method : {}", httpServletRequest.getMethod());
                    this.error = true;
                }
                //获取token
                this.accessToken = httpServletRequest.getHeader(RequestHandlerContext.HEADER_AUTHORIZATION);
                if (StringUtils.isEmpty(this.accessToken)) {
                    this.accessToken = httpServletRequest.getParameter(RequestHandlerContext.PARAMETER_ACCESS_TOKEN);
                }
                if (StringUtils.hasText(this.accessToken) && this.accessToken.toLowerCase().startsWith(RequestHandlerContext.PREFIX_BEARER)) {
                    this.accessToken = this.accessToken.substring(RequestHandlerContext.PREFIX_BEARER.length());
                }
            }
        }

    }
}
