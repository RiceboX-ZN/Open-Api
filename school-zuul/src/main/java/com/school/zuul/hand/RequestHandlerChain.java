package com.school.zuul.hand;

import com.school.zuul.entity.RequestHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestHandlerChain {

    private final List<RequestHandler> requestHandlerChain;

    @Autowired
    public RequestHandlerChain(List<RequestHandler> requestHandlerChain) {
        //升序排序
        this.requestHandlerChain = requestHandlerChain
                .stream()
                .sorted(Comparator.comparingInt(RequestHandler::getOrder))
                .collect(Collectors.toList());
    }

    public RequestHandlerContext handler(RequestHandlerContext requestHandlerContext) {
        for (RequestHandler requestHandler : requestHandlerChain) {
            //无需处理，则返回
            if (!requestHandler.handler(requestHandlerContext)) {
                break;
            }
        }
        return requestHandlerContext;
    }

}
