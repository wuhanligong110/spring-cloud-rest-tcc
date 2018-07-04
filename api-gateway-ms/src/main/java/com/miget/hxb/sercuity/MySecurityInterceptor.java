package com.miget.hxb.sercuity;

import com.miget.hxb.jwt.JsonWebTokenAuthentication;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.*;

/**
 * @Description 拦截器
 * @Author ErnestCheng
 * @Date 2017/6/23.
 */
@Component
public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);

    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {

        InterceptorStatusToken token = super.beforeInvocation(fi);

        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            if(SecurityContextHolder.getContext().getAuthentication() instanceof JsonWebTokenAuthentication){
                JsonWebTokenAuthentication authentication = (JsonWebTokenAuthentication)SecurityContextHolder.getContext().getAuthentication();
                ctx.addZuulRequestHeader("Authorization", authentication.getJsonWebToken());
                Map<String,List<String>> requestMap = ctx.getRequestQueryParams();
                if(requestMap == null){
                    requestMap = new HashMap<>();
                }
                requestMap.put("userId", Arrays.asList(authentication.getPrincipal().getUsername()));
                ctx.setRequestQueryParams(requestMap);
            }
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }

    }
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {

    }

}
