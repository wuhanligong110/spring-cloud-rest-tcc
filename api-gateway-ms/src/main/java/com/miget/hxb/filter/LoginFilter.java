package com.miget.hxb.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;
import com.miget.hxb.jwt.AuthTokenDetails;
import com.miget.hxb.jwt.JsonWebTokenUtility;
import com.miget.hxb.model.vo.AuthTokenVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @Description 登入过滤器
 * 登录后获得登录信息，生成token
 * @Author ErnestCheng
 * @Date 2017/6/26.
 */
public class LoginFilter extends ZuulFilter {

    private  static  final Logger logger= Logger.getLogger(LoginFilter.class);

    @Autowired
    private JsonWebTokenUtility tokenService;

    @Value("${jwt.user.login:/users/login}")
    private String loginUrl;

    @Value("${jwt.user.weixinLogin:/users/weixin/login}")
    private String weixinLoginUrl;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public final Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String requestURI = ctx.getRequest().getRequestURI();
        if(requestURI.contains(loginUrl) || requestURI.contains(weixinLoginUrl)){
            final InputStream responseDataStream = ctx.getResponseDataStream();
            final String responseData;
            try {
                responseData = CharStreams.toString(new InputStreamReader(responseDataStream,"UTF-8"));
                ctx.setResponseBody(responseData);
                Map<String,Object> value = JSONObject.parseObject(responseData,Map.class);
                if(value!=null&&value.get("data")!=null){
                    Map<String,Object> data = JSONObject.parseObject(value.get("data").toString());
                    AuthTokenDetails authTokenDetails = new AuthTokenDetails(Long.valueOf(data.get("userId").toString()),data.get("mobile").toString(),null);
                    String jwt=tokenService.initKey().createJsonWebToken(authTokenDetails);
                    if(jwt!=null){
                        AuthTokenVO authTokenVO=new AuthTokenVO();
                        authTokenVO.setToken(jwt);
                        authTokenVO.setUserId(Long.valueOf(data.get("userId").toString()));
                        ctx.setResponseBody(JSON.toJSON(authTokenVO).toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
