package com.bsy.business.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsy.business.user.bean.User;
import com.bsy.business.user.dao.IUserDao;

@Service
public class UserService {
	@Autowired
	private IUserDao userDao;
	
	public List<User> getUsers(Map<String, Object> param){
		return userDao.getUserInfo(param);
	}
	
	public Object getCount(Map<String, Object> param){
		return userDao.getUserCount(param);
	}
}
