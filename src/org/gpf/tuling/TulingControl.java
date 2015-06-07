package org.gpf.tuling;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.gpf.util.IConst;

/**
 * 图灵机器人接口流程控制类
 * 
 * @author gaopengfei
 * @date 2015年6月6日 下午4:58:43
 */
public class TulingControl {

	/**
	 * 调用图灵机器人接口，返回所需内容
	 * 
	 * @param info用户指定的内容信息
	 * @return
	 */
	public String getTulingResult(String info) {

		// 给图灵机器人发送get请求
		String tlResult = HttpGetRequest.get(IConst.TULING_URL + info);
		StringBuilder sb = new StringBuilder();

		System.out.println("收到图灵机器人返回的json数据：" + tlResult);
		// 解析图灵机器人返回的json结果，提取内容
		if (tlResult != null) {
			JSONObject json = JSONObject.fromObject(tlResult);

			sb.append(json.getString("text") + "\n");
			if (json.get("url") != null) {
				sb.append(json.getString("url") + "\n");
			}
			if (json.get("list") != null) {
				JSONArray list = JSONArray.fromObject(json.get("list"));
				for (int i = 0; i < list.size(); i++) {
					JSONObject o = (JSONObject) list.get(i);
					if (o.get("detailurl") != null) {
						sb.append(o.getString("detailurl") + "\n");
					}
				}
			}
		} else {
			sb.append("淫家听不懂的耶。/::<\n");
		}
		return sb.toString();
	}
}
