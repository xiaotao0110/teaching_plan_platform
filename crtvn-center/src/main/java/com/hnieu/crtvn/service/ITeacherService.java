package com.hnieu.crtvn.service;

import com.hnieu.crtvn.dto.TeacherDTO;
import com.hnieu.crtvn.entity.Teacher;
import com.hnieu.crtvn.vo.TeacherVO;

import java.util.List;
import java.util.Map;

public interface ITeacherService {
	
	Map<String, Object> findTeacherByPage(int pageNo, int pageSize, TeacherDTO teacherDTO);

	List<Map<String,Object>> findTeacher(int collegeId, int professionId);
	
	TeacherVO findTeacherVOById(TeacherDTO teacherDTO);

	void updateTeacherManner(TeacherDTO teacherDTO);
		
	Teacher findTeacherById(int id);
	
	long findTeacherCount();
}
