package org.gpf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gpf.service.WeChatService;
import org.gpf.util.CheckUtil;

public class WeChatIOServlet extends HttpServlet {

	private static final long serialVersionUID = -1700830016699206388L;

	/**
	 * 给微信服务器发送回应，验证token
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收微信服务器以Get请求发送的4个参数
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr); // 校验通过，原样返回echostr参数内容
		}
	}

	/**
	 * 处理微信客户端发送的post请求
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置接口的数据格式和编码方式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");

		// 返回结果(从service层得到)给微信客户端
		PrintWriter out = response.getWriter();
		String result = new WeChatService().responseWeChatClient(request,
				response);
		out.print(result);
		out.flush();
		out.close();
	}

}
