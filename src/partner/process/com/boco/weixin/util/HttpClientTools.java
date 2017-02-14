package com.boco.weixin.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HttpClientTools {
	private  static Logger logger = Logger.getLogger(HttpClientTools.class);

	public static String requestByHttpPost(String reqUrl, Map<String, String> params)  {
		
		String res = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000); //连接超时时间30000ms
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		PostMethod postMethod = new PostMethod(reqUrl);
		try {
			NameValuePair[] data = new NameValuePair[params.size()];
			int i = 0;
			for (Iterator<String> it = params.keySet().iterator();it.hasNext();) {
				String key = it.next();
				String value = params.get(key);
				NameValuePair pair = new NameValuePair(key, value);
				data[i] = pair;
				i++;
			}
			postMethod.setRequestBody(data);
			int statusCode = httpClient.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				return null;
			} else {
				res = new String(postMethod.getResponseBody(), "utf-8");
				return res;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return res;
	}
	
	public static String requestByHttpGet(String reqUrl){
		HttpClient httpClient  = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		GetMethod getMethod = new GetMethod(reqUrl);
		try {
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode == HttpStatus.SC_OK){
				byte[] responseBody = getMethod.getResponseBody();
				return new String(responseBody, "utf-8");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}
	public static void main(String[] args) {
		String appid="wx1b406dc2d985cfc9";
		String secret="54f05543fe13a59aa7dd5480f715cf9f";
		String access_tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		String access_tokenResult = HttpClientTools.requestByHttpGet(access_tokenUrl);
		System.out.println("获取access_token  json串："+access_tokenResult);
		JSONObject jsStr = JSONObject.fromObject(access_tokenResult); 
		String access_token=jsStr.getString("access_token");
		System.out.println("获取access_token："+access_token);
		String reqUrl="http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+access_token;
		String result = HttpClientTools.requestByHttpGet(reqUrl);
		System.out.println(result);
		


		
	}
}
