package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Classroom;

import java.util.List;
import java.util.Map;

public interface IClassroomDAO {
	
	//分页
	List findClassroomByPage(int pageNo, int pageSize, Map<String, Object> params);

	List<Classroom> findClassroomByPId(Map<String, Object> params);
	
	void updateClassroom(Classroom classroom);
	
	Classroom findClassroomById(int id);
	
	long findClassroomCountByGrid(Map<String, Object> params);
	
	List<Classroom> findClassroomSeatsCount(Map<String, Object> params);
	
	long getMaxClassroomId();
	
	List<Classroom> findClassroomByMark(int status);
	
}
