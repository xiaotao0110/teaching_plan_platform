package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.IClassroomDAO;
import com.hnieu.crtvn.dto.ClassroomDTO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Classroom;
import com.hnieu.crtvn.service.IClassroomService;
import com.hnieu.crtvn.vo.ClassroomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClassroomServiceImpl implements IClassroomService {

	@Autowired
	private HttpServletRequest request;
	
	private static final int CLASSROOM_ALL = 0; //0:表示全部

	@Autowired
	private IClassroomDAO  classroomDAO;
	
	@Override
	public List<Map<String, Object>> findClassroom(ClassroomDTO classroomDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();				
		
		if(classroomDTO.getCollegeId() != CLASSROOM_ALL){			
			params.put("collegeId", classroomDTO.getCollegeId());
		}
		
		List<Classroom>  classroomList = classroomDAO.findClassroomByPId(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object>  classroomMap = new HashMap<String,Object>();
		
		classroomMap.put("text","全部");
		classroomMap.put("id",0);
		list.add(classroomMap);
		
		for(int i=0; i < classroomList.size(); i++){	
			classroomMap = new HashMap<String,Object>();
			classroomMap.put("text",classroomList.get(i).getBuilding()+"教");
			classroomMap.put("id",classroomList.get(i).getBuilding());
			list.add(classroomMap);
	    }		
		return list;
	}

	@Override
	public Map<String, Object> findClassroomByPage(int pageNo, int pageSize, ClassroomDTO classroomDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(classroomDTO.getCollegeId() != CLASSROOM_ALL){			
			params.put("collegeId", classroomDTO.getCollegeId());
		}
		
		if(classroomDTO.getBuilding()!= CLASSROOM_ALL){			
			params.put("building", classroomDTO.getBuilding());
		}
		
		if(classroomDTO.getMark() != CLASSROOM_ALL){
			params.put("mark", classroomDTO.getMark());
		}
		
		if(classroomDTO.getStatus() != CLASSROOM_ALL){
			params.put("status", classroomDTO.getStatus());
		}
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		List<ClassroomVO> resultList = classroomDAO.findClassroomByPage(pageNo, pageSize, params);
		
		int collegeId = academician.getCollege().getId();
			
		for(int i=0; i<resultList.size();i++){
			if(resultList.get(i).getCollegeId() == collegeId){
				resultList.get(i).setThisId(collegeId);
			}
		}
				
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",classroomDAO.findClassroomCountByGrid(params));
		resultMap.put("rows", resultList);
        
		return resultMap;
	}

	@Override
	public ClassroomVO findClassroomVOById(ClassroomDTO classroomDTO) {
		Map<String,Object> params = new HashMap<String,Object>();		
		
		params.put("collegeId", classroomDTO.getCollegeId());
		params.put("building", classroomDTO.getBuilding());
		params.put("mark", classroomDTO.getMark());
		params.put("classroomId", classroomDTO.getClassroomId());
		
		List<ClassroomVO> classroomVOList = classroomDAO.findClassroomByPage(1, 1, params);
				
		return classroomVOList.get(0);
	}

	@Override
	public void updateClassroomManner(ClassroomDTO classroomDTO) {
			
		Classroom classroom= findClassroomById(classroomDTO.getClassroomId());
		classroom.setMark(classroomDTO.getMark());	 
		classroom.setNumber(classroomDTO.getNumber());
		
		classroomDAO.updateClassroom(classroom);
	}

	@Override
	public Classroom findClassroomById(int id) {

		return classroomDAO.findClassroomById(id);
	}

}
