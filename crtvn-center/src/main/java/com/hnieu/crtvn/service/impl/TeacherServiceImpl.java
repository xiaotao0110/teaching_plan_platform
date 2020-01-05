package com.hnieu.crtvn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hnieu.crtvn.dao.ITeacherDAO;
import com.hnieu.crtvn.dto.TeacherDTO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Course;
import com.hnieu.crtvn.entity.Teacher;
import com.hnieu.crtvn.service.ITeacherService;
import com.hnieu.crtvn.vo.TeacherVO;

@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService {

	@Autowired
	private  HttpServletRequest request;
	
	private static final int TEACHER_ALL = 0; //0:表示全部

	@Autowired
	private ITeacherDAO teacherDAO;
	
	@Override
	public List<Map<String, Object>> findTeacher(int collegeId,int professionId) {
		
		Map<String,Object> params = new HashMap<String,Object>();				
		params.put("collegeId", collegeId);
		params.put("professionId", professionId);
		
		List<Teacher>  teacherList = teacherDAO.findTeacher(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> teacherMap = new HashMap<String,Object>();
		
		teacherMap.put("text","全部");
		teacherMap.put("id",0);
		list.add(teacherMap);
		
		for(int i=0; i < teacherList.size(); i++){	
			teacherMap = new HashMap<String,Object>();
			teacherMap.put("text",teacherList.get(i).getCode());
			teacherMap.put("id",teacherList.get(i).getId());
			list.add(teacherMap);
	    }		
		return list;
	}

	@Override
	public Map<String, Object> findTeacherByPage(int pageNo, int pageSize, TeacherDTO teacherDTO) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");		
		
		int collegeId = academician.getCollege().getId();
		
		List<TeacherVO> resultList =  teacherDAO.findTeacherByPage(pageNo, pageSize, getParsms(teacherDTO));
		
		for(int i=0; i<resultList.size();i++){
			if(resultList.get(i).getCollegeId() == collegeId){
				resultList.get(i).setThisId(collegeId);
			}
		}
	
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",teacherDAO.findTeacherCountByGrid(getParsms(teacherDTO)));
		resultMap.put("rows", resultList);
        
		return resultMap;
	}

	@Override
	public TeacherVO findTeacherVOById(TeacherDTO teacherDTO) {

		List<TeacherVO>  teacherVOList = teacherDAO.findTeacherByPage(1, 1, getParsms(teacherDTO));
				
		return teacherVOList.get(0);
	}
	
	private Map<String,Object> getParsms(TeacherDTO teacherDTO){
		
		Map<String,Object> params = new HashMap<String,Object>();

		if(teacherDTO.getCollegeId() != TEACHER_ALL){			
			params.put("collegeId", teacherDTO.getCollegeId());
		}
		
		if(teacherDTO.getProfessionId() != TEACHER_ALL){
			params.put("professionId", teacherDTO.getProfessionId());
		}
		
		if(teacherDTO.getTeacherId() != TEACHER_ALL){
			params.put("teacherId", teacherDTO.getTeacherId());
		}
		
		if(teacherDTO.getCourseId() != TEACHER_ALL){
			params.put("courseId", teacherDTO.getCourseId());
		}
		
		if(teacherDTO.getTeacherName()!=null && 
				teacherDTO.getTeacherName().length() != TEACHER_ALL){
			params.put("teacherName", "%"+teacherDTO.getTeacherName()+"%");
		}
		
		if(teacherDTO.getMark() != TEACHER_ALL){
			params.put("mark", teacherDTO.getMark());
		}
		
		if(teacherDTO.getStatus() != TEACHER_ALL){
			params.put("status", teacherDTO.getStatus());
		}
		
		return params;
		
	}

	@Override
	public void updateTeacherManner(TeacherDTO teacherDTO) {
		
		Teacher teacher = findTeacherById(teacherDTO.getTeacherId());
		teacher.setMark(teacherDTO.getMark());	 
		 
		teacherDAO.updateTeacher(teacher);
	}

	@Override
	public Teacher findTeacherById(int id) {

		return teacherDAO.findTeacherById(id);
	}

	@Override
	public long findTeacherCount() {

		return teacherDAO.findTeacherCount();
	}
}
