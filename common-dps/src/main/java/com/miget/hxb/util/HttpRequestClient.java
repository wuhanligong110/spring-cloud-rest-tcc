package com.miget.hxb.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpRequestClient {
	
	protected final static Logger LOGGER = LoggerFactory.getLogger(HttpRequestClient.class);

	public static String invokePost(String reqURL, List<NameValuePair> parameters, String charSet,HttpClient httpClient) throws Exception {
		if(charSet==null) charSet = "UTF-8";
		if(httpClient == null){
			if(reqURL.startsWith("https")){
				httpClient = HttpsClient.newHttpsClient();
			} else {
				httpClient = new DefaultHttpClient();
			}
		}
		HttpPost httpPost = new HttpPost(reqURL);
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, charSet);
		httpPost.setEntity(formEntity);
		LOGGER.info("HTTP POST请求地址: " + httpPost.getURI());
		httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String data = EntityUtils.toString(httpResponse.getEntity(),charSet);
		LOGGER.info("HTTP POST请求结果: data = {}",data);
		return data;
	}
	
	public static String invokePostJson(String reqURL, Object object, String charSet,HttpClient httpClient) throws Exception {
		if(charSet==null) charSet = "UTF-8";
		if(httpClient == null){
			if(reqURL.startsWith("https")){
				httpClient = HttpsClient.newHttpsClient();
			} else {
				httpClient = new DefaultHttpClient();
			}	
		}
		HttpPost httpPost = new HttpPost(reqURL);
		String jsonData = "{}";
		if(object instanceof String){
			jsonData = (String)object;
		} else {
			jsonData = JSONObject.toJSONString(object);
		}
        StringEntity entity = new StringEntity(jsonData,charSet);//解决中文乱码问题     
        entity.setContentType("application/json");
		httpPost.setEntity(entity);
		LOGGER.info("HTTP POST请求地址: {},请求参数={}",httpPost.getURI(),jsonData);
		httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String data = EntityUtils.toString(httpResponse.getEntity(),charSet);
		LOGGER.info("HTTP POST请求结果: data = {}",data);
		return data;
	}
	
	public static String invokePostXML(String reqURL, String xmlData, String charSet,HttpClient httpClient) throws Exception {
		if(charSet==null) charSet = "UTF-8";
		if(httpClient == null){
			if(reqURL.startsWith("https")){
				httpClient = HttpsClient.newHttpsClient();
			} else {
				httpClient = new DefaultHttpClient();
			}	
		}
		HttpPost httpPost = new HttpPost(reqURL);
        StringEntity entity = new StringEntity(xmlData,charSet);//解决中文乱码问题       
        entity.setContentType("application/x-www-form-urlencoded");
		httpPost.setEntity(entity);
		LOGGER.info("HTTP POST请求地址: " + httpPost.getURI());
//		httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String data = EntityUtils.toString(httpResponse.getEntity(),charSet);
		LOGGER.info("HTTP POST请求结果: data = {}",data);
		return data;
	}
	
	public static String invokeGet(String reqURL,List<NameValuePair> parameters,String charSet,HttpClient httpClient) throws Exception {
		if(httpClient == null){
			if(reqURL.startsWith("https")){
				httpClient = HttpsClient.newHttpsClient();
			} else {
				httpClient = new DefaultHttpClient();
			}	
		}
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(reqURL);
        if(parameters != null && parameters.size() > 0){
        	if (!reqURL.contains("?")) {
        		requestURL.append("?");
        	}
        	requestURL.append(EntityUtils.toString(new UrlEncodedFormEntity(parameters)));
        }
		HttpGet httpGet = new HttpGet(requestURL.toString());
		LOGGER.info("HTTP GET请求地址: " + httpGet.getURI());
		httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		HttpResponse httpResponse = httpClient.execute(httpGet); //执行GET请求
		String data = EntityUtils.toString(httpResponse.getEntity(), charSet==null ? "UTF-8" : charSet);
		LOGGER.info("HTTP GET请求结果: data = {}",data);
		return data;
	}
}
