package com.hnieu.crtvn.service;

import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.vo.AcademicianVO;

import java.util.Map;

public interface IAcademicianService {

	Map<String, Object> findAcademicianByPage(int pageNo, int pageSize, AcademicianVO academicianVO);

	Academician findAcademicianByName(Academician academician); 
	
	void deleteAcademician(AcademicianVO academicianVO);
	
	void addAcademician(AcademicianVO academicianVO) throws Exception;
}
