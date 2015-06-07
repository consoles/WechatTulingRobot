package org.gpf.tuling;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Http Get请求类
 * 
 * @author gaopengfei
 * @date 2015年6月6日 下午4:45:12
 */
public class HttpGetRequest {

	/**
	 * 
	 * @param url请求的地址和参数
	 * @return url的结果
	 */
	public static String get(String url) {

		String result = null;
		try {
			HttpGet request = new HttpGet(url);
			// 执行get请求
			HttpResponse response = HttpClients.createDefault()
					.execute(request);
			// 根据返回码判断请求是否成功
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
