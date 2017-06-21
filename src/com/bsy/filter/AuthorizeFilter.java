package com.bsy.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
/**
 * 权限验证
 * Author: liqi   Date: Jun 20, 2017  
 * @Description: 判定当前url请求在不在该用户拥有的合法请求中
 */
public class AuthorizeFilter implements Filter{
	private String[] noFiltUrls = {};
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String str = filterConfig.getInitParameter("noFiltUrls");
		if(StringUtils.isNotBlank(str))
			noFiltUrls = str.split(",");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		//1-web.xml 过滤我们不希望走过滤器的url 如，登录页
		if(noFilterUrl(request)){
			chain.doFilter(req, res);
		//2-用户登录验证
		}else if(loginVerify(request)){
			//url 访问权限验证
			if(!urlVerify(request)){
				response.sendRedirect(request.getContextPath()+"/errorPage/noPermission.html");
				return;
			}
			chain.doFilter(req, res);
		//3-没用登录的用户
		}else{
			response.sendRedirect(request.getContextPath()+"/business/login/login");
		}
		return;
	}
	
	//url地址合法性验证
	private boolean urlVerify(HttpServletRequest request) {
	    String modelIds = request.getSession().getAttribute("userModelIds").toString();
		Map<String, Object> url_map = (Map<String, Object>) request.getServletContext().getAttribute("url_map");
		//地址校验: 第一个/为项目名称  2,3,4位为模块地址位  后面的 / 为参数位
		String visitUrl = request.getRequestURI();
		String[] strs = visitUrl.split("/");
		StringBuffer sb = new StringBuffer();
		for(int i=2; i < strs.length; i++){
		    sb.append("/");
		    sb.append(strs[i]);
		    if(i > 3 ) break;
		}
		String key = sb.toString();
		String modelId = url_map.get(key) == null ? "-1" : url_map.get(key).toString();
		if(modelIds.contains(modelId))
		    return true;
	    return false;
	}

	//用户登录 session验证
	private boolean loginVerify(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") != null)
			return true;
		return false;
	}
	//不再过滤范围内的页面
	private boolean noFilterUrl(HttpServletRequest request) {
	    String uri = request.getRequestURI();
		for(String str : noFiltUrls){
			if(uri.contains(str)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		
	}
	
}
