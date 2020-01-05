package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Academician;

import java.util.List;
import java.util.Map;

public interface IAcademicianDAO {
	
	//分页
	List findAcademicianByPage(int pageNo, int pageSize, Map<String, Object> params);
	
	List<Academician> findAcademicianByName(Academician academician);
	    
	void deleteAcademician(Academician academician);
	
	void addAcademician(Academician academician);
	
	Academician findAcademicianById(int id);
	
	List<Academician> findAcademician();
}
