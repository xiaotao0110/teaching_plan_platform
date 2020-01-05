package com.hnieu.crtvn.service;

import java.util.Map;

import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.vo.AcademicianVO;

public interface IAcademicianService {

	Map<String, Object> findAcademicianByPage(int pageNo, int pageSize, AcademicianVO academicianVO);

	Academician findAcademicianByName(Academician academician); 
	
	void deleteAcademician(AcademicianVO academicianVO);
	
	void addAcademician(AcademicianVO academicianVO) throws Exception;
}
