package com.bsy.business.user.dao;

import java.util.List;
import java.util.Map;

import com.bsy.business.user.bean.User;

public interface IUserDao {
	//条件查询
	List<User> getUserInfo(Map<String, Object> param);
	
	//获取count
	Object getUserCount(Map<String, Object> param);
}
