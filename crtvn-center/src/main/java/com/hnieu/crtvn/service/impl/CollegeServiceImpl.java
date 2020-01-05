package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.ICollegeDAO;
import com.hnieu.crtvn.entity.College;
import com.hnieu.crtvn.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CollegeServiceImpl implements ICollegeService {

	@Autowired
	private ICollegeDAO collegeDAO;

	@Override
	public List<Map<String, Object>> findCollege() {
		
		List<College>  collegeList = collegeDAO.findCollege();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> collegeMap = new HashMap<String,Object>();
		
		collegeMap.put("text","全部");
		collegeMap.put("id",0);
		list.add(collegeMap);
		
		for(int i=0; i < collegeList.size(); i++){	
			collegeMap = new HashMap<String,Object>();
			collegeMap.put("text",collegeList.get(i).getName());
			collegeMap.put("id",collegeList.get(i).getId());
			list.add(collegeMap);
	    }		
		return list;

	}

}
