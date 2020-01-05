package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Profession;

import java.util.List;
import java.util.Map;

public interface IProfessionDAO {
	
	//分页
	List findProfessionByPage(int pageNo, int pageSize, Map<String, Object> params);

	//非分页 下拉框
	List<Profession> findProfession(Map<String, Object> params);

	long findProfessionCount();

	Profession findProfessionByCourseId(Map<String, Object> params);
}
