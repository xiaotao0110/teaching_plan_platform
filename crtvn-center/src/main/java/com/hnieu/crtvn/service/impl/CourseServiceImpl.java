package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.ICourseDAO;
import com.hnieu.crtvn.dao.IExamDAO;
import com.hnieu.crtvn.dao.IExaminationDAO;
import com.hnieu.crtvn.dto.CourseDTO;
import com.hnieu.crtvn.entity.Course;
import com.hnieu.crtvn.entity.Exam;
import com.hnieu.crtvn.entity.Examination;
import com.hnieu.crtvn.service.ICourseService;
import com.hnieu.crtvn.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

	private static final int COURSE_ALL = 0; //0:表示全部
	
	@Autowired
	private  ICourseDAO courseDao;
	
	@Autowired
	private IExaminationDAO examinationDAO;
	
	@Autowired
	private IExamDAO examDAO;
	
	@Override
	public Map<String, Object>  findCourseByPage(int pageNo, int pageSize, CourseDTO courseDTO) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(courseDTO.getProfessionId()!=COURSE_ALL){
			params.put("professionId", courseDTO.getProfessionId());
		}
		if(courseDTO.getCourseId()!=COURSE_ALL){
			params.put("courseId", courseDTO.getCourseId());
		}
		if(courseDTO.getCourseName() !=null && 
				courseDTO.getCourseName().length()!=COURSE_ALL){
			params.put("name", "%"+courseDTO.getCourseName()+"%");
		}
		if(courseDTO.getCourseManner()!=COURSE_ALL){
			params.put("manner", courseDTO.getCourseManner());
		}
		
		if(courseDTO.getCourseMark() != COURSE_ALL){
			params.put("mark", courseDTO.getCourseMark());
		}		
		
		List<CourseVO> resultList = courseDao.findCourseByPage(pageNo, pageSize, params);
	
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",courseDao.findCourseCountByGrid(params));
		resultMap.put("rows", resultList);
        
		return resultMap;
	}

	@Override
	public CourseVO findCourseVOById(CourseDTO courseDTO) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("courseId", courseDTO.getCourseId());
		params.put("professionId", courseDTO.getProfessionId());
		
		List<CourseVO>  courseVOList = courseDao.findCourseByPage(1,1,params);
				
		return courseVOList.get(0);
	}

	@Override
	public List<Map<String,Object>> findCourse(CourseDTO courseDTO) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("professionId", courseDTO.getProfessionId());
		
		if(courseDTO.getCourseManner() != 0 ){
			params.put("manner", courseDTO.getCourseManner());
		}
		
		if(courseDTO.getCourseMark() != 0 ){
			params.put("mark", courseDTO.getCourseMark());
		}
		
		List<Course>  courseList = courseDao.findCourseByPId(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> courseMap = new HashMap<String,Object>();
		
		courseMap.put("text","全部");
		courseMap.put("id",0);
		list.add(courseMap);
		
		for(int i=0; i < courseList.size(); i++){	
			courseMap = new HashMap<String,Object>();
			courseMap.put("text",courseList.get(i).getName());
			courseMap.put("id",courseList.get(i).getId());
			list.add(courseMap);
	    }		
		return list;
	}

	@Override
	public Course findCourseById(int id) {
		
		return courseDao.findCourseById(id);
	}

	@Override
	public void updateCourseManner(CourseDTO courseDTO) {
		
		Course course = findCourseById(courseDTO.getCourseId());
		course.setManner(courseDTO.getCourseManner());	 
		 
		 courseDao.updateCourse(course);
	}

	@Override
	public List<Map<String,Object>> findCourseByTId(CourseDTO courseDTO) {

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("collegeId", courseDTO.getCollegeId());
		params.put("professionId", courseDTO.getProfessionId());
		params.put("teacherId", courseDTO.getTeacherId());
		
		List<CourseVO>  courseList = courseDao.findCourseByTId(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> courseMap = new HashMap<String,Object>();
		
		courseMap.put("text","全部");
		courseMap.put("id",0);
		list.add(courseMap);
		
		for(int i=0; i < courseList.size(); i++){	
			courseMap = new HashMap<String,Object>();
			courseMap.put("text",courseList.get(i).getCourseName());
			courseMap.put("id",courseList.get(i).getCourseId());
			list.add(courseMap);
	    }		
		return list;
	}

	@Override
	public long findCourseCount() {

		return courseDao.findCourseCount();
	}

	@Override
	public void updateCourseMark(CourseDTO courseDTO) {

		Course course = findCourseById(courseDTO.getCourseId());
		course.setMark(courseDTO.getCourseMark());	 
		 
		courseDao.updateCourse(course);
		
		//删除有关该课程的排考信息
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("courseId", courseDTO.getCourseId());
		List<Examination> examinations = 
				examinationDAO.findExaminationByCourseId(params);
		List<Exam> exams = null;
		Examination examination = null;
		if(examinations.size() != 0){
			for(int i=0;i<examinations.size();i++){
				examination = examinations.get(i);
				params.clear();
				params.put("examinationId", examination.getId());
				exams = examDAO.findExamByEId(params);
				
				for(int j=0;j<exams.size();j++){
					examDAO.deleteExam(exams.get(j));
				}
				
			}
		}
		for(int i=0;i<examinations.size();i++){
			examinationDAO.deleteExamination(examinations.get(i));			
		}
		
		
	}
}
