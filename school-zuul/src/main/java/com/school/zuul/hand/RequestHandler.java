package com.school.zuul.hand;

import com.school.zuul.entity.RequestHandlerContext;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandler {

    boolean handler(RequestHandlerContext requestHandlerContext);

    /**
     * 数值越大，越靠后执行
     * @return
     */
    int getOrder();

}
