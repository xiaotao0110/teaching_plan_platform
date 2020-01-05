package com.hnieu.crtvn.service;

import java.util.List;
import java.util.Map;

import com.hnieu.crtvn.dto.ClassroomDTO;
import com.hnieu.crtvn.entity.Classroom;
import com.hnieu.crtvn.vo.ClassroomVO;

public interface IClassroomService {
		
	Map<String, Object> findClassroomByPage(int pageNo, int pageSize, ClassroomDTO classroomDTO);
	
	List<Map<String,Object>> findClassroom(ClassroomDTO classroomDTO);
	
	ClassroomVO findClassroomVOById(ClassroomDTO classroomDTO) ;
	
	void updateClassroomManner(ClassroomDTO classroomDTO);
	
	Classroom findClassroomById(int id);
	
}
