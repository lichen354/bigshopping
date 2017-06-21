package com.bsy.business.model.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsy.business.model.dao.IModelDao;

@Service
public class ModelService {
	@Autowired
	private IModelDao modelDao;
	//getModelInfo
	public List<Map<String, Object>> getModelInfo(Map<String, Object> param){
		return modelDao.getModelInfo(param);
	}
	//getModelCount
	public List<Map<String, Object>> getModelCount(Map<String, Object> param){
		return modelDao.getModelCount(param);
		
	}
	
}
