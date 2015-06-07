package org.gpf.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gpf.util.WeChatControl;

public class WeChatService {

	public String responseWeChatClient(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 读取微信客户端post发送的消息(XML数据)
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		
		String str = null;
		StringBuilder sb = new StringBuilder();

		// 按行读取内容
		while (null != (str = br.readLine())) {
			sb.append(str);
		}
		br.close();

		return new WeChatControl().wechatProcess(sb.toString());
	}
}
