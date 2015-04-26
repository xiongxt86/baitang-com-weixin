package com.hzboy.weixin.core.user;
//package com.hzboy.weixin.core.user;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.hzboy.weixin.util.JsonUtils;
//@RequestMapping("user")
//@Controller
//public class UserController {
//	@RequestMapping(value = "/save.action", method = RequestMethod.POST)
//	public @ResponseBody String save(HttpServletRequest request, HttpServletResponse response,@Validated({ Second.class}) UserModel userModel,BindingResult result) throws IOException{
//	    response.setCharacterEncoding("utf-8");
//	    System.out.println("----"+userModel.getUsername());
//	    if(result.hasErrors()){
//	        List<ObjectError> list = result.getAllErrors();
//	        for(ObjectError objectError:list){
//	            System.out.println(objectError.getDefaultMessage());
//	        }
//	        JsonUtils.write(list, response.getWriter());;
//	        return null;
//	    }
//	    response.getWriter().write("rrrrrr");
//	    return null;
//	}
//	@RequestMapping(value = "/update.action", method = RequestMethod.POST)
//	public @ResponseBody String update(HttpServletRequest request, HttpServletResponse response,@Validated({First.class, Second.class}) UserModel user,BindingResult result) throws IOException{
//	    response.setCharacterEncoding("utf-8");
//	    if(result.hasErrors()){
//	        List<ObjectError> list = result.getAllErrors();
//	        for(ObjectError objectError:list){
//	            System.out.println(objectError);
//	        }
//	        JsonUtils.write(list, response.getWriter());;
//	        return null;
//	    }
//	    response.getWriter().write("rrrrrr");
//	    return null;
//	}
//}
