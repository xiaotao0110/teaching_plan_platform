package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Student;
import com.hnieu.crtvn.entity.Studentselection;

import java.util.List;
import java.util.Map;

public interface IStudentDAO {
	
	//分页
	List findStudentByPage(int pageNo, int pageSize, Map<String, Object> params);

	//非分页 考试下拉框
	List findCourseYesBySid(Map<String, Object> params);

	//非分页 非考试下拉框
	public List findCourseNoBySid(Map<String, Object> params);

	void updateStudentCourse(Studentselection studentselection);

	void insertStudentCourse(Studentselection studentselection);

	Student findStudentById(int id);

	Studentselection findStudentselectionById(int studentId, int courseId);

	List<Student> findStudentCount(Map<String, Object> params);

	long findStudentCountByGrid(Map<String, Object> params);

	List<Studentselection> findStudentselection(Map<String, Object> params);
	
}
