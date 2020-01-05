package com.hnieu.crtvn.util;

import com.hnieu.crtvn.dao.*;
import com.hnieu.crtvn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class Reduction {
	
	@Autowired
	private IClassroomDAO classroomDAO;

	@Autowired
	private ICourseDAO courseDAO;
	
	@Autowired
	private IEtimeDAO etimeDAO;

	@Autowired
	private ITeacherDAO teacherDAO;
	
	@Autowired
	private IExaminationDAO examinationDAO;
	
	@Autowired
	private IExamDAO examDAO ;
	
	private static final int STATUS_1 = 1; //0:表示已安排
	
	private static final int STATUS_2 = 2; //0:表示未安排
	
	public  void revert()throws Exception{
		Map<String,Object> params = new HashMap<>();
		try{
			//还原考场状态
			List<Classroom> classrooms = classroomDAO.findClassroomByMark(STATUS_1); 
			Classroom classroom = null;
			for(int i=0;i<classrooms.size();i++){
				classroom = classrooms.get(i);
				classroom.setStatus(STATUS_2);
				classroomDAO.updateClassroom(classroom);
			}
			
			//还原课程
			List<Course> courses = courseDAO.findCourseByMark(STATUS_1);
			Course course = null;
			for(int i=0;i<courses.size();i++){
				course = courses.get(i);
				course.setMark(STATUS_2);
				courseDAO.updateCourse(course);
			}
			
			//还原时间
			List<Etime> etimes = etimeDAO.findEtimeByMark(STATUS_1);
			Etime etime = null;
			for(int i=0;i<etimes.size();i++){
				etime = etimes.get(i);
				etime.setMark(STATUS_2);
				etimeDAO.updateEtime(etime);
			}
			
			//还原教师
			List<Teacher> teachers = teacherDAO.findTeacherByStatus(STATUS_1);
			Teacher teacher = null;
			for(int i=0;i<teachers.size();i++){
				teacher = teachers.get(i);
				teacher.setStatus(STATUS_2);
				teacherDAO.updateTeacher(teacher);
			}
			
			
			//删除考试
			examDAO.truncateExam();

			//删除考场	 
			List<Examination> examinations = examinationDAO.findExaminationByMark(params);
			Examination examination = null;
			for(int i=0;i<examinations.size();i++){
				examination = examinations.get(i);
				examinationDAO.deleteExamination(examination);
			}
					
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("重置失败！");
		}
		
		
	}
	
	
	public  void revertExam()throws Exception{
		try{
			Map<String,Object> params = new HashMap<>();
			//删除考试
			examDAO.truncateExam();
			
			//还原考场实际人数
			List<Examination> examinations = examinationDAO.findExaminationByMark(params);
			Examination examination = null;
			for(int i=0;i<examinations.size();i++){
				examination = examinations.get(i);
				examination.setNumbers(examination.getClassroom().getSeats());
				examinationDAO.updateExamination(examination);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("重置失败！");
		}
		
		
	}
	
}
