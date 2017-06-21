package com.bsy.business.model.dao;

import java.util.List;
import java.util.Map;

public interface IModelDao {
	//getModelInfo
	List<Map<String, Object>> getModelInfo(Map<String, Object> param);
	//getModelCount
	List<Map<String, Object>> getModelCount(Map<String, Object> param);
	
	/**
	 * 根据用户id获取modelId
	 * @param userId
	 * @return List<Map<"id", id>>
	 */
	List<Map<String, Object>> getModelIdByUser(String userId);
}
