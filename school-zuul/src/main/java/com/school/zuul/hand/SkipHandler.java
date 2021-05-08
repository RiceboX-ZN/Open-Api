package com.school.zuul.hand;


import com.netflix.zuul.context.RequestContext;
import com.school.zuul.config.AuthenticationConfigurationProperties;
import com.school.zuul.entity.RequestHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class SkipHandler implements RequestHandler {

    @Autowired
    private AuthenticationConfigurationProperties authenticationConfigurationProperties;

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * 处理：否匹配白名单
     *
     * @param requestHandlerContext
     * @return
     */
    @Override
    public boolean handler(RequestHandlerContext requestHandlerContext) {
        //没有权限，则全部放行
        if (!authenticationConfigurationProperties.getEnable()) {
            log.info("no activated,Allow all request to access");
            return false;
        }
        //白名单不为空
        if (!CollectionUtils.isEmpty(authenticationConfigurationProperties.getSkipPathPrefix())) {
            //没有匹配成功代表true
            boolean match = authenticationConfigurationProperties.getSkipPathPrefix()
                    .stream()
                    .anyMatch(skip -> MATCHER.match(skip, requestHandlerContext.getUri()));
            if (!match) {
                log.debug("not match white path!");
                return false;
            }
            requestHandlerContext.setAllow(match);
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
