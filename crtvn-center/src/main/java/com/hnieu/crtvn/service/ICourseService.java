package com.hnieu.crtvn.service;

import java.util.List;
import java.util.Map;

import com.hnieu.crtvn.dto.CourseDTO;
import com.hnieu.crtvn.entity.Course;
import com.hnieu.crtvn.vo.CourseVO;

public interface ICourseService {
	
	Map<String, Object> findCourseByPage(int pageNo, int pageSize, CourseDTO courseDTO);
	
	//非分页 下拉框
	List<Map<String,Object>> findCourse(CourseDTO courseDTO);
		
	List<Map<String,Object>> findCourseByTId(CourseDTO courseDTO);
	
	void updateCourseManner(CourseDTO courseDTO);
	
	CourseVO findCourseVOById(CourseDTO courseDTO);
	
	Course findCourseById(int id);
	
	long findCourseCount();
	
	void updateCourseMark(CourseDTO courseDTO);
}
