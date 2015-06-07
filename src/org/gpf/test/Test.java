package org.gpf.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test {

	/**
	 * 模拟微信客户端的post请求
	 * 
	 * @param url请求地址
	 * @param param请求内容
	 * @return接口返回的内容
	 */
	private static String post(String url, String param) {

		try {
			HttpPost request = new HttpPost(url);
			request.setEntity(new StringEntity(param));
			HttpResponse response = HttpClients.createDefault()
					.execute(request);

			// 根据返回码判断请求是否成功
			if (200 == response.getStatusLine().getStatusCode()) {
				return EntityUtils.toString(response.getEntity());
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = URLEncoder.encode("你是哪里人", "utf-8");
		String url = "http://127.0.0.1/TulingRobot/wechatio.do";
		String param = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["
				+ content + "]]></Content></xml>";

		String result = post(url, param);
		System.out.println(result);
	}
}
