package com.miget.hxb.wx.utils;

import com.eshop4j.core.util.CommonUtils;
import com.eshop4j.xoss.helper.XmlHelper;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 微信支付的统一下单工具类
 * 
 */
public class PaymentKit {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentKit.class);

    /**
     * 组装签名的字段
     * @param params 参数
     * @param urlEncoder 是否urlEncoder
     * @return String
     */
    public static String packageSign(Map<String, String> params, boolean urlEncoder) {
        // 先将参数以其参数名的字典序升序进行排序
        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> param : sortedParams.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                try { value = urlEncode(value); } catch (UnsupportedEncodingException e) {}
            }
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * urlEncode
     * @param src 微信参数
     * @return String
     * @throws UnsupportedEncodingException 编码错误
     */
    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, Charsets.UTF_8.name()).replace("+", "%20");
    }

    /**
     * 生成签名
     * @param params 参数
     * @param paternerKey 支付密钥
     * @return sign
     */
    public static String createSign(Map<String, String> params, String paternerKey) {
        // 生成签名前先去除sign
        params.remove("sign");
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + paternerKey;
        return HashKit.md5(stringSignTemp).toUpperCase();
    }

    /**
     * 支付异步通知时校验sign
     * @param params 参数
     * @param paternerKey 支付密钥
     * @return {boolean}
     */
    public static boolean verifyNotify(Map<String, String> params, String paternerKey){
        String sign = params.get("sign");
        String localSign = PaymentKit.createSign(params, paternerKey);
        return sign.equals(localSign);
    }

    /**
     * 微信下单，map to xml
     * @param params 参数
     * @return String
     */
    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Entry<String, String> entry : params.entrySet()) {
            String key   = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StringUtils.isEmpty(value)) continue;
            xml.append("<").append(key).append(">");
                xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        String map2MXL = xml.toString();
        LOGGER.info("map转xml对象结果,map2MXL={}",map2MXL);
        return map2MXL;
    }
    
    /**
     * 微信下单，object to xml
     * @param params 参数
     * @return String
     */
    public static String objToXml(Object obj) {
    	Map<String, String> params = CommonUtils.obj2Map(obj);
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Entry<String, String> entry : params.entrySet()) {
            String key   = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StringUtils.isEmpty(value)) continue;
            xml.append("<").append(key).append(">");
                xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        String map2MXL = xml.toString();
        LOGGER.info("map转xml对象结果,map2MXL={}",map2MXL);
        return map2MXL;
    }

    /**
     * 针对支付的xml，没有嵌套节点的简单处理
     * @param xmlStr xml字符串
     * @return map集合
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        XmlHelper xmlHelper = XmlHelper.of(xmlStr);
        return xmlHelper.toMap();
    }

}
