package com.hzboy.weixin.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import core.SignUtil;


/**
 * 
 * @ClassName: DeveloperValidate 
 * @Description: TODO(验证开发者) 
 * @author xiongxiaotun@126.com 
 * @date 2015年4月21日 下午5:31:18 
 *
 */
@Controller
public class DeveloperValidate {
	
	@RequestMapping("auth")
	public void auth(HttpServletResponse response, String signature,String timestamp,String nonce,String echostr){
		 PrintWriter out;
		try {
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
				out.print(echostr);  
			}  
			out.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
