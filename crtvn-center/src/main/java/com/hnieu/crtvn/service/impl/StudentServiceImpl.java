package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.ICourseDAO;
import com.hnieu.crtvn.dao.IProfessionDAO;
import com.hnieu.crtvn.dao.IStudentDAO;
import com.hnieu.crtvn.dto.CourseDTO;
import com.hnieu.crtvn.dto.StudentDTO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Course;
import com.hnieu.crtvn.entity.Student;
import com.hnieu.crtvn.entity.Studentselection;
import com.hnieu.crtvn.service.IStudentService;
import com.hnieu.crtvn.vo.CourseVO;
import com.hnieu.crtvn.vo.StudentVO;
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
@SuppressWarnings("unchecked")
public class StudentServiceImpl implements IStudentService {

	private static final int STUDENT_ALL = 0; //0:表示全部
	
	private static final int COURSE_YES = 1; //考试科目
	private static final int COURSE_NO = 2; //非考试科目
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IStudentDAO studentDAO;
	
	@Autowired
	private ICourseDAO courseDAO;
	
	@Autowired
	private IProfessionDAO professionDAO;
	
	@Override
	public Map<String, Object> findStudentByPage(int pageNo, int pageSize, StudentDTO studentDTO) {
		
		List<StudentVO> resultList =  studentDAO.findStudentByPage(pageNo, pageSize, getParams(studentDTO));
		
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",studentDAO.findStudentCountByGrid(getParams(studentDTO)));
		resultMap.put("rows", resultList);
        
		return resultMap;
	}


	@Override
	public StudentVO findStudentVOById(StudentDTO studentDTO) {

		List<StudentVO> resultList =  studentDAO.findStudentByPage(1, 1, getParams(studentDTO));

		return resultList.get(0);
	}
	
	private Map<String,Object> getParams(StudentDTO studentDTO){
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");		
		
		Map<String,Object> params = new HashMap<String,Object>();
				
		if(studentDTO.getProfessionId()!= STUDENT_ALL){
			params.put("professionId", studentDTO.getProfessionId());
		}
		if(studentDTO.getClasssId()!=STUDENT_ALL){
			params.put("classsId", studentDTO.getClasssId());
		}
		
		if(studentDTO.getStudentName()!=null && studentDTO.getStudentName().length()!=STUDENT_ALL){
			params.put("studentName", "%"+studentDTO.getStudentName()+"%");
		}
		if(studentDTO.getSID()!=null && studentDTO.getSID().length()!=STUDENT_ALL){
			params.put("SID", "%"+studentDTO.getSID()+"%");
		}
		
		if(studentDTO.getStudentName()!=null && studentDTO.getStudentName().length()!=STUDENT_ALL){
			params.put("studentName", "%"+studentDTO.getStudentName()+"%");
		}
		
		if(studentDTO.getStudentId()!=STUDENT_ALL){
			params.put("studentId", studentDTO.getStudentId());
		}
		
		params.put("collegeId", academician.getCollege().getId());	
		
		return params;
		
	}


	@Override
	public List<Map<String, Object>> findStudentByYesMark(StudentDTO studentDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("studentId", studentDTO.getStudentId());		
		
		List<CourseVO>  courseYesList = studentDAO.findCourseYesBySid(params);	
		
		params.put("mark", studentDTO.getStudentId());
		List<CourseVO>  courseNoList = studentDAO.findCourseNoBySid(params);
		
		for(int i=0; i<courseYesList.size();i++){
			for(int j=0;j<courseNoList.size();j++){
				if(courseYesList.get(i).getCourseId()==courseNoList.get(j).getCourseId()){
					courseYesList.remove(i);
				}
			}
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> courseVOMap = new HashMap<String,Object>();

		for(int i=0; i < courseYesList.size(); i++){	
			courseVOMap = new HashMap<String,Object>();
			courseVOMap.put("text",courseYesList.get(i).getCourseName());
			courseVOMap.put("id",courseYesList.get(i).getCourseId());
			list.add(courseVOMap);
	    }	

		return list;
	}

	@Override
	public List<Map<String, Object>> findStudentByNoMark(StudentDTO studentDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("studentId", studentDTO.getStudentId());		
		
		params.put("mark", studentDTO.getMark());
		List<CourseVO>  courseNoList = studentDAO.findCourseNoBySid(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> courseVOMap = new HashMap<String,Object>();

		for(int i=0; i < courseNoList.size(); i++){	
			courseVOMap = new HashMap<String,Object>();
			courseVOMap.put("text",courseNoList.get(i).getCourseName());
			courseVOMap.put("id",courseNoList.get(i).getCourseId());
			list.add(courseVOMap);
	    }	

		return list;
	}
	
	
	
	@Override
	public void updateStudentCourse(StudentDTO studentDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("studentId", studentDTO.getStudentId());
		
		List<CourseVO>  courseNoList = studentDAO.findCourseNoBySid(params);	
		boolean flag = true ;
		for(int j=0;j<courseNoList.size();j++){
			if(courseNoList.get(j).getCourseId() == studentDTO.getCourseId()){
				flag = false;
				break;
			}
		}
		
		if(flag){//新增
			
			Course course = courseDAO.findCourseById(studentDTO.getCourseId());
			Student student = studentDAO.findStudentById(studentDTO.getStudentId());

			Studentselection studentselection = new Studentselection();
			studentselection.setCourse(course);
			studentselection.setStudent(student);
			
			studentselection.setMark(studentDTO.getMark());
			if(studentDTO.getMark() == COURSE_YES){
				studentselection.setReason("");
			}else if(studentDTO.getMark() == COURSE_NO){
				studentselection.setReason(studentDTO.getReason());
			}
			
			
			
			studentDAO.insertStudentCourse(studentselection);
			
		}else{//修改
			
			Studentselection studentselection = 
					studentDAO.findStudentselectionById(studentDTO.getStudentId(), studentDTO.getCourseId());
			
			studentselection.setMark(studentDTO.getMark());
			if(studentDTO.getMark() == COURSE_YES){
				studentselection.setReason("");
			}else if(studentDTO.getMark() == COURSE_NO){
				studentselection.setReason(studentDTO.getReason());
			}
			
			studentDAO.updateStudentCourse(studentselection);
		}		
		
	}


	@Override
	public long findStudentCount() {
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("collegeId", academician.getCollege().getId());
		
		int number = studentDAO.findStudentCount(params).size();
		
		return number;
	}


	@Override
	public String findStudentselection(StudentDTO studentDTO) {

		Studentselection studentselection = 
				studentDAO.findStudentselectionById(studentDTO.getStudentId(), studentDTO.getCourseId());
		
		return studentselection.getReason();
	}


	@Override
	public long studentCountbyCourse(StudentDTO studentDTO) {

		int professionId ;
		Map<String, Object> params = new HashMap<String, Object>();		
		
		params.put("courseId", studentDTO.getCourseId());		
		professionId = professionDAO.findProfessionByCourseId(params).getId();
		
		params.clear();
		params.put("professionId", professionId);				
		int count = studentDAO.findStudentCount(params).size();		
		
		return count >=0 ? count : 0;
	}


	@Override
	public long findNotExam(CourseDTO courseDTO) {
		
		Map<String, Object> params = new HashMap<String, Object>();	
		
		params.put("courseId", courseDTO.getCourseId());
		params.put("mark", 2);
		
		List<Studentselection> sl = studentDAO.findStudentselection(params);
		
		return sl.size();
	}
	
	
	
}
