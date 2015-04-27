package com.hzboy.weixin.core.user;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzboy.weixin.util.JsonUtils;

import core.StringUtile;

@Controller  
@RequestMapping("user")
public class UserController {  
 
   @RequestMapping("save") 
   @ResponseBody
   public Object save(HttpServletResponse response,@ModelAttribute @Valid User user, BindingResult result) {  
	   response.setContentType("text/html;charset=UTF-8");
	   if(result.hasErrors()) {  
    	   try {
    		   String[] array = StringUtile.toArray(result.getAllErrors(),"defaultMessage");
    		   JsonUtils.write(array, response.getWriter());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   return null;
	    }  
       return "成功！";  
   }  
} 