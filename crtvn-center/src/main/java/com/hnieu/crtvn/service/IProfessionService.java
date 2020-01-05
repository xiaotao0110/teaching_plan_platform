package com.hnieu.crtvn.service;

import java.util.List;
import java.util.Map;

public interface IProfessionService {

	Map<String, Object>  findProfessionByPage(int pageNo, int pageSize);
	
	List<Map<String,Object>>  findProfession(int CollegeId);
	
	long findProfessionCount();
}
