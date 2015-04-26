package com.hzboy.weixin.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @ClassName: DeveloperValidate 
 * @Description: TODO(验证开发者) 
 * @author xiongxiaotun@126.com 
 * @date 2015年4月21日 下午5:31:18 
 *
 */
@Controller
@RequestMapping("develop")
public class DeveloperValidate {
	
	@RequestMapping("auth")
	@ResponseBody
	public String auth(Model model){
		return "success";
	}
}
