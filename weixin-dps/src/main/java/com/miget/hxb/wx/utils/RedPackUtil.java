package com.miget.hxb.wx.utils;

import com.miget.hxb.wx.constant.WeixinConstant;
import com.miget.hxb.util.HttpRequestClient;
import org.apache.http.client.HttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 红包工具类
 */
public class RedPackUtil {
	/**
	 * 生成商户订单号
	 * @param mch_id  商户号
	 * @param userId  该用户的userID
	 * @return
	 */
	public static String createBillNo(){
		//组成： mch_id+yyyymmdd+10位一天内不能重复的数字
		//10位一天内不能重复的数字实现方法如下:
		//因为每个用户绑定了userId,他们的userId不同,加上随机生成的(10-length(userId))可保证这10位数字不一样
		Date dt=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
		String nowTime= df.format(dt);
		int length = 10 ;
		return WeixinConstant.MCH_ID + nowTime  + getRandomNum(length);
	}
	/**
	 * 生成特定位数的随机数字
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}
	
	/**
	 * 红包
	 * @param requestUrl
	 * @param xmlData
	 * @return
	 * @throws Exception
	 */
	public static String RedPackPost(String requestUrl,String xmlData) throws Exception{ 
		HttpClient httpclient = WeixinUtil.getWXHttpsclient();
        return HttpRequestClient.invokePostXML(requestUrl, xmlData, null, httpclient);
    }
	
	/**
	 * 企业付款
	 * @param requestUrl
	 * @param xmlData
	 * @return
	 * @throws Exception
	 */
	public static String TransfersPost(String requestUrl,String xmlData) throws Exception{ 
		HttpClient httpclient = WeixinUtil.getWXHttpsclient();
        return HttpRequestClient.invokePostXML(requestUrl, xmlData, null, httpclient);
    }
}