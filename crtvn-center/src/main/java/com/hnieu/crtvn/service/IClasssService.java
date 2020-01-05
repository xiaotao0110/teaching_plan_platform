package com.hnieu.crtvn.service;

import com.hnieu.crtvn.dto.ClasssDTO;
import com.hnieu.crtvn.vo.ClasssVO;

import java.util.List;
import java.util.Map;

public interface IClasssService {

	Map<String, Object> findClasssByPage(int pageNo, int pageSize, ClasssDTO classsDTO);
	
	//非分页 下拉框
	List<Map<String,Object>>  findClasss(ClasssDTO classsDTO);
	
	//非分页 下拉框
	List<Map<String,Object>>  findClasssByPId(ClasssDTO classsDTO);
	
	int getStudentCount(ClasssDTO classsDTO);
	
	ClasssVO findClasssVOById(ClasssDTO classsDTO);
}
