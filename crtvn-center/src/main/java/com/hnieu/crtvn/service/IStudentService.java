package com.hnieu.crtvn.service;

import java.util.List;
import java.util.Map;

import com.hnieu.crtvn.dto.CourseDTO;
import com.hnieu.crtvn.dto.StudentDTO;
import com.hnieu.crtvn.vo.StudentVO;

public interface IStudentService {
	
	Map<String, Object> findStudentByPage(int pageNo, int pageSize, StudentDTO studentDTO);

	StudentVO findStudentVOById(StudentDTO studentDTO);

	List<Map<String,Object>> findStudentByYesMark(StudentDTO studentDTO);
	
	List<Map<String,Object>> findStudentByNoMark(StudentDTO studentDTO);
	
	void updateStudentCourse(StudentDTO studentDTO);
	
	long findStudentCount();
	
	String findStudentselection(StudentDTO studentDTO);
	
	long studentCountbyCourse(StudentDTO studentDTO);
	
	long findNotExam(CourseDTO courseDTO);
}
