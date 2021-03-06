package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Classs;

import java.util.List;
import java.util.Map;

public interface IClasssDAO {

	List findClasss(Map<String, Object> params);

	List findClasssByPId(Map<String, Object> params);

	List getStudentCount(Map<String, Object> params);

	//分页
	List findClasssByPage(int pageNo, int pageSize, Map<String, Object> params);
	
	long findClasssCountByGrid(Map<String, Object> params);
	
	Classs findClasssById(Integer id);
}
