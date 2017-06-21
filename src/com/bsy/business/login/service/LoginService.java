package com.bsy.business.login.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsy.business.model.dao.IModelDao;
import com.bsy.business.user.bean.User;
import com.bsy.business.user.dao.IUserDao;

@Service
public class LoginService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IModelDao modelDao;
	
	//方法代码禁止对数据库进行修改
	@Transactional(readOnly=true)
	public List<User> getUser(Map<String, Object> map){
		return userDao.getUserInfo(map);
		
	}
	/**
	 * 获取当前用户的modelId并拼接成String
	 * @param userId
	 * @return modelId1&modelId2&modelId3&modelId4......&modelIdn
	 */
	public String getModelIds(String userId) {
	    List<Map<String, Object>> list = modelDao.getModelIdByUser(userId);
	    StringBuffer sb = new StringBuffer();
	    for(Map<String, Object> m : list){
	        sb.append(m.get("id"));
	        sb.append("&");
	    }
		return sb.toString();
	}
	
}
