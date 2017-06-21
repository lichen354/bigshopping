package com.bsy.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bsy.business.model.service.ModelService;
/**
 * 监听系统启动
 * Author: liqi   Date: Jun 20, 2017  
 * @Description: TODO
 */
public class CacheListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent e) {}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		System.out.println(time+" : 系统启动中...");
		ServletContext ctx = e.getServletContext();
		//加载模块信息到容器中  
		//在web环境下获取spring上下文
		ModelService modelService = WebApplicationContextUtils.getWebApplicationContext(e.getServletContext()).getBean(ModelService.class);
		
		List<Map<String, Object>> list = modelService.getModelInfo(new HashMap<String, Object>());
		Map<String, Object> url_map = urlMap(list);
		ctx.setAttribute("url_map", url_map);
	}
	
	
	//将list中信息 存为Map<"url", "id">
	private Map<String, Object> urlMap(List<Map<String, Object>> list) {
	    Map<String, Object> url_map = new HashMap<>();
	    for(Map<String, Object> m : list){
	        if(m.get("href") == null) continue;
			url_map.put(m.get("href").toString(), m.get("id"));
		}
		return url_map;
	}
	
}
