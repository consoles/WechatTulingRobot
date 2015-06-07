package org.gpf.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

public class CheckUtil {

	/**
	 * 微信服务器用于验证地址的有效性
	 * 
	 * @param signature	微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return 验证结果
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {

		String[] arr = { IConst.WEIXIN_TOKEN, timestamp, nonce };

		// 排序
		Arrays.sort(arr);
		// 生成字符串
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}

		// sha1加密
		String temp = DigestUtils.sha1Hex(content.toString());

		// 与微信传递过来的签名进行比较
		return temp.equals(signature);
	}

}
