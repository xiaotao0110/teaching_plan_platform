package com.hnieu.crtvn.dao;

import java.util.List;
import java.util.Map;

import com.hnieu.crtvn.entity.Course;

public interface ICourseDAO {
	
	//分页
	List findCourseByPage(int pageNo, int pageSize, Map<String, Object> params);
	
	//非分页 下拉框   课程模块
	List<Course> findCourseByPId(Map<String, Object> params);
	
	//非分页 下拉框 教师模块
	List findCourseByTId(Map<String, Object> params);
		
	void updateCourse(Course course);
	
	Course findCourseById(int id);
	
	//统计信息
	long findCourseCount();
	
	long findCourseCountByGrid(Map<String, Object> params);
	
	long getMaxCourseId();
	
	List<Course> findCourseByMark(int mark);
}
