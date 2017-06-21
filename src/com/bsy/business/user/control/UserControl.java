package com.bsy.business.user.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bsy.business.user.bean.User;
import com.bsy.business.user.service.UserService;

@Controller
@RequestMapping("/business/user")
public class UserControl {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public ModelAndView showUsers(){
		ModelAndView mdv = new ModelAndView("user/user_list");
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		List<User> list = userService.getUsers(param);
		Object count = userService.getCount(param);
		
		mdv.addObject("list", list);
		mdv.addObject("count", count);
		return mdv;
	}
	
	@RequestMapping("/add")
	public String addUser(HttpServletRequest req){
		//获取参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("", "");
		
		
		return "user/user_add";
	}
}
