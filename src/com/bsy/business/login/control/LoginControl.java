package com.bsy.business.login.control;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bsy.business.login.service.LoginService;
import com.bsy.business.user.bean.User;
import com.bsy.utils.RandromCodeUtil;

/**
 * 用于登录的控制类
 */
@Controller
@RequestMapping("/business/login")
public class LoginControl {
	@Autowired
	private LoginService service;
	
	
	@RequestMapping("/login")
	public String loginPage(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		//检查cookies 如果存在则自动登录
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie c : cookies){
				if("autoLogin".equals(c.getName())){
					String userId = c.getValue();
					map.put("id", userId);
					List<User> user_list = service.getUser(map);
					if(user_list.size() > 0){
						//查询到用户 保存到session 跳转至首页
						request.getSession().setAttribute("user", user_list.get(0));
						request.getSession().setAttribute("userModelIds", service.getModelIds(user_list.get(0).getId()));
						
						return "/index";
					}
				}
			}
		}
		
		
		//
		String token = RandromCodeUtil.getToken();
		request.getSession().setAttribute("token", token);
		//void 默认返回prefix+/login+.suffix 即，返回login.jsp
		return "/login/login";
	}
	/**
	 * 验证码
	 */
	@RequestMapping("/checkCode")
	public void getCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//验证码
		char[] codes = RandromCodeUtil.getRandomCode(4);
		
		//验证码存入session
		HttpSession session = request.getSession();
		session.setAttribute("Randomcode", new String(codes));

		OutputStream out = response.getOutputStream();
		//画
		BufferedImage buffImg = RandromCodeUtil.getImg(codes, 90, 20, 20);
		ImageIO.write(buffImg,"png",out);
		
		//禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        
        //关闭流
        out.close();
	}
	
	@RequestMapping("/check")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<>();
		
		//防止表单重复提交 step1
		String formToken = request.getParameter("token");
		Object sessToken = request.getSession().getAttribute("token");
		System.out.println("formToken:"+formToken+"  sessToken:"+sessToken);
		if(sessToken==null || formToken==null || !formToken.equals(sessToken)){
			return "repeat";
		}
			
		String account = request.getParameter("username");
		String pwd = request.getParameter("password");
		map.put("account", account);
		map.put("password", pwd);
		
		String result = "fail";
		List<User> user_list = service.getUser(map);
		if(user_list.size() > 0){
			//登录成功
			request.getSession().setAttribute("user", user_list.get(0));
			request.getSession().setAttribute("userModelIds", service.getModelIds(user_list.get(0).getId()));
			result = "success";
			//写入cookie
			String autoLogin = request.getParameter("autoLogin");
			System.out.println("autoLogin="+autoLogin);
			if(autoLogin != null){
				Cookie cookie = new Cookie("autoLogin", user_list.get(0).getId());
				response.addCookie(cookie);
			}
			//防止表单重复提交 step2 在登录成功的if块中
			request.getSession().removeAttribute("token");
		}
		return result;
	}
	@RequestMapping("/wrong3timesCheck")
	@ResponseBody
	public String checkCode(HttpServletRequest request){
		String checkCode = request.getParameter("checkCode");
		Object sessCheck = request.getSession().getAttribute("Randomcode");
		if(checkCode.equals(sessCheck)){
			return "success";
		}else{
			return "wrong";
		}
	}
	
	private void demo(HttpServletRequest request){
		//spring上下文  在web环境下获取spring上下文
        WebApplicationContext ctx1 = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        //springmvc的上下文  
        WebApplicationContext ctx2=RequestContextUtils.getWebApplicationContext(request);  
	}
	
}
