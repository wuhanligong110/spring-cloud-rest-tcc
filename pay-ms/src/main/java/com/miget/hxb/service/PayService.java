package com.miget.hxb.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Preconditions;
import com.miget.hxb.Shift;
import com.miget.hxb.controller.StatusCode;
import com.miget.hxb.controller.client.AccountClient;
import com.miget.hxb.controller.client.OrderClient;
import com.miget.hxb.controller.client.ProductClient;
import com.miget.hxb.domain.BizOrder;
import com.miget.hxb.domain.SysBusinessWeixinConfig;
import com.miget.hxb.domain.CrmUser;
import com.miget.hxb.model.request.ConfigRequest;
import com.miget.hxb.model.request.PlaceOrderRequest;
import com.miget.hxb.model.request.PrepayRequest;
import com.miget.hxb.model.request.WithdrawRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.service.enchashment.EnchashmentContext;
import com.miget.hxb.service.enchashment.EnchashmentStrategyFactory;
import com.miget.hxb.util.HttpRequestClient;
import com.miget.hxb.wx.utils.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.miget.hxb.wx.utils.WeixinUtil.checkWXResult;

/**
 * @author Zhao Junjian
 */
@Service
public class PayService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PayService.class);

    @Resource
    private AccountClient accountClient;
    @Resource
    private ProductClient productClient;
    @Resource
    private OrderClient orderClient;

    public Map<String, String> placeOrder(PrepayRequest prepayRequest) {

        Map<String, String> returnParams = new HashMap<>();

        LOGGER.info("调用统一下单接口请求参数,prepayRequest={}", JSONObject.toJSONString(prepayRequest));

        //订单id  和 openid
        String out_trade_no = WeixinUtil.getUUID();
        Long userId = prepayRequest.getUserId();

        Long total_fee = 0L;

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setAddressId(prepayRequest.getAddressId());
        placeOrderRequest.setOrderItems(prepayRequest.getOrderItems());
        placeOrderRequest.setUserId(Long.valueOf(userId));
        placeOrderRequest.setOutTradeNo(out_trade_no);
        ObjectDataResponse<BizOrder> dataOrder = orderClient.placeOrder(placeOrderRequest);
        BizOrder preOrder;
        if(dataOrder.getCode() == 20000){
            preOrder = dataOrder.getData();
            total_fee = preOrder.getAmount();
            returnParams.put("amount", total_fee.toString());
            returnParams.put("orderId", preOrder.getOrderId().toString());
        }else if(dataOrder.getCode() == 42003){
            returnParams.put("errorMessage", "订单信息有误!");
        }else if(dataOrder.getCode() == 42005){
            returnParams.put("errorMessage", "库存不足!");
        }
        return returnParams;
    }

    private CrmUser remoteUserByOpenId(String openId){
        ObjectDataResponse<CrmUser> dataResponse = accountClient.queryRemoteUserByOpenId(openId);
        if(dataResponse != null && dataResponse.getData() != null){
            return  dataResponse.getData();
        }else {
            Shift.fatal(StatusCode.USER_NOT_EXISTS);
        }
        return null;
    }

    private SysBusinessWeixinConfig remoteWeixinConfig(Long businessId){
        Preconditions.checkNotNull(businessId);
        ObjectDataResponse<SysBusinessWeixinConfig> dataResponse =  productClient.weixinConfig(businessId);
        if(dataResponse != null && dataResponse.getData() != null){
            return dataResponse.getData();
        }else{
            Shift.fatal(StatusCode.WEIXIN_CONFIG_NULL);
        }
        return null;
    }

    /**
     * 获取当前进入微信公众号用户的openid
     * @param request
     * @param response
     * @return
     */
    public String getUserOpenId(HttpServletRequest request, HttpServletResponse response){
        Object openIdObj = request.getSession().getAttribute("openId");
        Long businessId = (Long) request.getSession().getAttribute("businessId");
        String openId = "";
        if(openIdObj!=null){
            openId = openIdObj.toString();
        }
        if (StringUtils.isEmpty(openId)) {
            openId = (String) request.getParameter("openId");
            if(StringUtils.isEmpty(openId)){
                String code = (String) request.getParameter("code");
                if(StringUtils.isEmpty(code)){
//					String requestURILocall = getRequestUrl(request);
//					LOGGER.info("通过页面重定向获取code,页面当前请求 URL={}",requestURILocall);
//					String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//					try {
//						requestUrl = requestUrl.replace("APPID",WeixinConstant.APPID).replace("REDIRECT_URI",URLEncoder.encode(requestURILocall,"UTF-8"));
//						LOGGER.info("通过页面重定向获取code,最终重定向 requestUrl={}",requestUrl);
//						response.sendRedirect(requestUrl);
//					} catch (IOException e) {
//						LOGGER.error("获取当前进入微信公众号用户的openid进行页面重定向异常",e);
//					}
                    LOGGER.error("获取当前进入微信公众号用户的openid异常,code为null");
                } else {
                    SysBusinessWeixinConfig weixinConfig = remoteWeixinConfig(businessId);
                    if(weixinConfig != null){
                        openId = getUserOpenId(weixinConfig.getAppId(), weixinConfig.getAppSecret(), code);
                    }
                }
            }
            request.getSession().setAttribute("openId", openId);
        }
        LOGGER.info("当前进入微信公众号用户的 openId={}",openId);
        if(StringUtils.isEmpty(openId)){
            openId = "oTZdswPIB9w6XtcIOjZx6nAKy7SA";
            LOGGER.info("获取openId为null 直接使用自己的 openId={}",openId);
        }
        return openId;
    }

    /**
     * 获取openId
     * @param appid
     * @param appSecret
     * @param code
     * @return
     * 采用 网页授权获取 access_token API：SnsAccessTokenApi获取
     */
    private String getUserOpenId(String appid, String appSecret, String code){
        LOGGER.info("根据code获取openId,code={}",code);
        String openId = "";
        try {
            String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
            requestUrl = requestUrl.replace("APPID", appid).replace("SECRET", appSecret).replace("CODE", code);
            String response = HttpRequestClient.invokeGet(requestUrl, null, null, null);
            if(checkWXResult(response)){
                Map<String, Object> resultMap = JSONObject.parseObject(response, new TypeReference<Map<String, Object>>() {});
                if (resultMap.get("openid") != null) {
                    openId = resultMap.get("openid").toString();
                }
            }
        } catch (Exception e) {
            LOGGER.error("根据code获取openId异常", e);
        }
        return openId;
    }

    public void enchashment(Long businessId, Long userId, WithdrawRequest request) {
        ConfigRequest configRequest = new ConfigRequest();
        configRequest.setConfigKey("enchashment_type");
        Integer enchashmentType = Integer.valueOf(productClient.sysConfig(businessId,configRequest).getData());
        EnchashmentContext context = new EnchashmentContext(EnchashmentStrategyFactory.createEnchashmentStrategy(enchashmentType));
        context.enchashment(businessId,userId,request.getUserAccount(),request.getAmount());
    }
}
