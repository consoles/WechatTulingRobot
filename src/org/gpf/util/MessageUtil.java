package org.gpf.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gpf.po.TextMessage;

import com.thoughtworks.xstream.XStream;

/**
 * 封装返回给微信的xml数据
 * 
 * @author gaopengfei
 * @date 2015年6月6日 下午4:19:45
 */
public class MessageUtil {

	public static final String MSG_TEXT = "text";

	/**
	 * 封装xml结果数据（把从微信客户端接收到的消息封装成回应的消息）
	 * 
	 * @param xml接收到的xml对象
	 * @param tlResult图灵的结果
	 *            (对我们有用的文本)
	 * @return
	 */
	public static String getXMLresult(TextMessage requestXML, String tlResult) {

		// 回应给微信客户端的xml数据需要更改实体的4个属性FromUserName、ToUserName、时间戳和文本内容
		TextMessage responseXML = new TextMessage();
		responseXML.setFromUserName(requestXML.getToUserName());
		responseXML.setToUserName(requestXML.getFromUserName());
		responseXML.setCreateTime(new Date().getTime() + "");
		responseXML.setContent(tlResult);
		responseXML.setMsgType(MSG_TEXT);

		System.out.println("回应给微信客户端的xml数据：\n" + textMessageToXML(responseXML));
		return textMessageToXML(responseXML);
	}

	/**
	 * 将文本消息对象转换成XML
	 */
	public static String textMessageToXML(TextMessage textMessage) {

		XStream xstream = new XStream(); // 使用XStream将实体类的实例转换成xml格式
		xstream.alias("xml", textMessage.getClass()); // 将xml的默认根节点替换成“xml”
		return xstream.toXML(textMessage);

	}

	public static TextMessage xmlToTextMessage(String xml) {

		System.out.println("微信客户端post方式提交的xml数据：\n" + xml);
		TextMessage msg = null;

		try {
			// 将字符串转化为xml对象
			Document doc = DocumentHelper.parseText(xml);

			// 获取根节点
			Element root = doc.getRootElement();
			// 遍历根节点下的所有子节点
			Iterator<?> iterator = root.elementIterator();

			// 利用反射机制调用实体类的set方法
			Class<?> c = Class.forName("org.gpf.po.TextMessage");
			// 创建实体类
			msg = (TextMessage) c.newInstance();

			while (iterator.hasNext()) {
				Element e = (Element) iterator.next();
				// 获取set方法中的参数字段(实体类的属性),getName方法获取节点名
				Field field = c.getDeclaredField(e.getName());
				// 获取set方法
				Method method = c.getDeclaredMethod("set" + e.getName(),
						field.getType());
				// 调用set方法
				method.invoke(msg, e.getText());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;

	}
}
