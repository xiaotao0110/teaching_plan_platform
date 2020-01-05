package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface ITeacherDAO {
	
	List<Teacher> findTeacher(Map<String, Object> params);

	List findTeacherByPage(int pageNo, int pageSize, Map<String, Object> params);

	void updateTeacher(Teacher teacher);

	Teacher findTeacherById(int id);

	long findTeacherCount();

	long findTeacherCountByGrid(Map<String, Object> params);

	List<Teacher> findTeacherByStatus(Map<String, Object> params);
	
	//mark：已安排，未安排 
	List<Teacher> findTeacherByStatus(int mark);
}
