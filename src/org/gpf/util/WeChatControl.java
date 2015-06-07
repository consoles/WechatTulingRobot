package org.gpf.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.gpf.po.TextMessage;
import org.gpf.tuling.TulingControl;

/**
 * 微信流程控制类
 * 
 * @author gaopengfei
 * @date 2015年6月6日 下午4:37:38
 */
public class WeChatControl {

	/**
	 * 微信处理流程控制
	 * 
	 * @param content
	 *            客户端发送的xml
	 * @return 处理完成，封装好的xml
	 * @throws UnsupportedEncodingException
	 */
	public String wechatProcess(String content)
			throws UnsupportedEncodingException {

		// 解析接收到的xml数据，转化为对象
		TextMessage xml = MessageUtil.xmlToTextMessage(content);

		// 调用图灵机器人接口处理模块，获取图灵机器人的结果
		String tlResult = new TulingControl().getTulingResult(URLEncoder
				.encode(xml.getContent(), "utf-8"));

		// 封装微信xml接口的数据，向微信客户端回应
		String xmlResult = MessageUtil.getXMLresult(xml, tlResult);

		return xmlResult;
	}
}
