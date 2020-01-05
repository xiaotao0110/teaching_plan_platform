package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IStudentDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Course;
import com.hnieu.crtvn.entity.Student;
import com.hnieu.crtvn.entity.Studentselection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class StudentDAOImpl extends BaseDao implements IStudentDAO {
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List findStudentByPage(int pageNo, int pageSize, Map<String, Object> params) {
		
		return findByPage(getHql(params), pageNo, pageSize, params);
	}

	@Override
	public long findStudentCountByGrid(Map<String, Object> params) {

		return find(getHql(params), params).size();
	}
	
	
	@Override
	public List findCourseYesBySid(Map<String, Object> params) {
		
		HttpSession session = request.getSession();
		Course course = (Course)session.getAttribute("course");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.CourseVO(c.id,c.name) " );					
		hql.append(" from Student as s , Classs as cs, ");
		hql.append(" Classselection as csl , Course as c ");
		
		hql.append(" where s.classs.id=cs.id ");		
		hql.append(" and  csl.classs.id=cs.id  ");
		hql.append(" and  csl.course.id=c.id  ");
		
		hql.append(" and  s.id=:studentId ");
		hql.append(" and c.startcd ="+"'"+course.getStartcd()+"'");

		return find(hql.toString(), params);
	}

	@Override
	public List findCourseNoBySid(Map<String, Object> params) {

		HttpSession session = request.getSession();
		Course course = (Course)session.getAttribute("course");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.CourseVO(c.id,c.name) " );					
		hql.append(" from Student as s , Course as c , ");
		hql.append(" Studentselection as stl ");
		
		hql.append(" where stl.student.id=s.id  ");
		hql.append(" and  stl.course.id=c.id  ");
		
		hql.append(" and  s.id=:studentId ");
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and  stl.mark=:mark ");
		}else{
			params.remove("mark");
		}
		hql.append(" and c.startcd ="+"'"+course.getStartcd()+"'");
		
		return find(hql.toString(), params);
	}

	@Override
	public void updateStudentCourse(Studentselection studentselection) {

		update(studentselection);
	}

	@Override
	public void insertStudentCourse(Studentselection studentselection) {

		save(studentselection);
	}

	@Override
	public Student findStudentById(int id) {

		return (Student) get(Student.class, id);
	}

	@Override
	public Studentselection findStudentselectionById(int studentId, int courseId) {

		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from Studentselection as stl");
		
		hql.append(" where stl.student.id=:studentId  ");
		hql.append(" and  stl.course.id=:courseId  ");
	
		params.put("studentId", studentId);
		params.put("courseId", courseId);
		
		return (Studentselection) find(hql.toString(), params).get(0);
				
	}

	@Override
	public List<Student> findStudentCount(Map<String,Object> params) {
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select s ");		
		hql.append(" from Profession as p ,College as co , Student as s  ,Classs as cs ");		
		hql.append(" where p.college.id=co.id ");
		hql.append(" and  cs.profession.id=p.id ");
		hql.append(" and  s.classs.id=cs.id ");				
		hql.append(" and co.id =:collegeId ");
			
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and  p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
		
		params.put("collegeId", academician.getCollege().getId());		
		
		return find(hql.toString(),params);
	}

	private String getHql(Map<String,Object> params){
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct  new com.hnieu.crtvn.vo.StudentVO("
				+ " s.id, co.id, co.name, p.id, p.name, cs.id, cs.code, s.sid, "
				+ " s.name, s.gender,s.age, s.idcard,s.phone,s.grade "
				+ ") ");	
				
		hql.append(" from College as co, Profession as p , ");
		hql.append(" Classs as cs, Student as s ");
		
		hql.append(" where  p.college.id=co.id ");		
		hql.append(" and  cs.profession.id=p.id ");
		hql.append(" and  s.classs.id=cs.id ");
		
		
		if(!StringUtils.isEmpty(params.get("collegeId"))){
			hql.append(" and  co.id=:collegeId ");
		}else{
			params.remove("collegeId");
		}
		
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and  p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
		
		if(!StringUtils.isEmpty(params.get("classsId"))){
			hql.append(" and  cs.id=:classsId ");
		}else{
			params.remove("classsId");
		}
		
		if(!StringUtils.isEmpty(params.get("studentName"))){
			hql.append(" and  s.name like :studentName ");
		}else{
			params.remove("studentName");
		}
		
		if(!StringUtils.isEmpty(params.get("SID"))){
			hql.append(" and  s.sid like :SID ");
		}else{
			params.remove("SID");
		}
		
		if(!StringUtils.isEmpty(params.get("studentId"))){
			hql.append(" and  s.id=:studentId ");
		}else{
			params.remove("studentId");
		}
		
		return hql.toString();
		
	}

	@Override
	public List<Studentselection> findStudentselection(Map<String,Object> params) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select sl ");		
		hql.append(" from College co, Profession p, Classs cs, Student s, Studentselection sl ");		
		hql.append(" where p.college.id=co.id ");
		hql.append(" and  cs.profession.id=p.id ");
		hql.append(" and  s.classs.id=cs.id ");	
		hql.append(" and  sl.student.id=s.id ");	
		
		hql.append(" and co.id =:collegeId ");
		hql.append(" and sl.course.id =:courseId ");
		hql.append(" and sl.mark =:mark ");
		
		params.put("mark", params.get("mark"));		
		params.put("courseId", params.get("courseId"));		
		params.put("collegeId", academician.getCollege().getId());
		
		
		return find(hql.toString(), params);
	}
}
