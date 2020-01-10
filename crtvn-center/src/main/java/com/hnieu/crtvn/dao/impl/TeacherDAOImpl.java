package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.ITeacherDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Teacher;
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
@SuppressWarnings("unchecked")
public class TeacherDAOImpl extends BaseDao<Teacher> implements ITeacherDAO {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List<Teacher> findTeacher(Map<String, Object> params) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct t from Teacher as t ,Profession as p ,College as co ");		
		
		hql.append(" where p.college.id = co.id ");
		hql.append(" and t.profession.id =p.id ");
		
		if((Integer)params.get("collegeId")!=0){
			hql.append(" and co.id =:collegeId ");
		}else{
			params.remove("collegeId");
		}
		
		if((Integer)params.get("professionId")!=0){
			hql.append(" and p.id =:professionId ");
		}else{
			params.remove("professionId");
		}
		
		return find(hql.toString(),params);
	}

	@Override
	public List findTeacherByPage(int pageNo, int pageSize, Map<String, Object> params) {
	
		return findByPage(getHql(params), pageNo, pageSize, params);
	}

	@Override
	public long findTeacherCountByGrid(Map<String, Object> params) {

		return find(getHql(params),  params).size();
	}
	
	@Override
	public void updateTeacher(Teacher teacher) {
		update(teacher);
		flush();
	}

	@Override
	public Teacher findTeacherById(int id) {
		
		 return get(Teacher.class, id);
	}

	@Override
	public long findTeacherCount() {
		Map<String,Object> params = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		StringBuffer hql = new StringBuffer();
		hql.append(" select t ");		
		hql.append(" from Profession as p ,College as co , Teacher as t ");		
		hql.append(" where p.college.id=co.id ");
		hql.append(" and  t.profession.id=p.id ");
		
		hql.append(" and co.id =:collegeId ");
				
		params.put("collegeId", academician.getCollege().getId());
		
		return find(hql.toString(),params).size();
	}
	
	private String getHql(Map<String, Object> params){
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct  new com.hnieu.crtvn.vo.TeacherVO("
				+ "co.id, co.name, p.id, p.name,c.id,c.code, c.name, "
				+ "t.id,t.code, t.name,t.gender,t.age, t.position, t.phone,t.mark,t.status) ");	
				
		hql.append(" from College as co, Profession as p, ");
		hql.append(" Course as c, Teacher as t, Teach as tt ");		
		hql.append(" where  p.college.id=co.id ");		
		hql.append(" and  c.profession.id=p.id ");
		hql.append(" and  tt.course.id=c.id");
		hql.append(" and  tt.teacher.id=t.id");
		
		
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
		
		if(!StringUtils.isEmpty(params.get("teacherId"))){
			hql.append(" and  t.id=:teacherId ");
		}else{
			params.remove("teacherId");
		}
		
		if(!StringUtils.isEmpty(params.get("courseId"))){
			hql.append(" and  c.id=:courseId ");
		}else{
			params.remove("courseId");
		}
		
		if(!StringUtils.isEmpty(params.get("teacherName"))){
			hql.append(" and  t.name like :teacherName ");
		}else{
			params.remove("teacherName");
		}
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and  t.mark=:mark ");
		}else{
			params.remove("mark");
		}
		
		if(!StringUtils.isEmpty(params.get("status"))){
			hql.append(" and  t.status=:status ");
		}else{
			params.remove("status");
		}
		return hql.toString();
	}

	@Override
	public List<Teacher> findTeacherByStatus(Map<String, Object> params) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct t from Teacher as t ,Profession as p ,College as co ");		
		
		hql.append(" where p.college.id = co.id ");
		hql.append(" and t.profession.id =p.id ");
		
		
		if(!StringUtils.isEmpty(params.get("collegeId"))){
			hql.append(" and co.id =:collegeId ");
		}else{
			params.remove("collegeId");
		}

		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and t.mark =:mark ");
		}else{
			params.remove("mark");
		}
		
		if(!StringUtils.isEmpty(params.get("status"))){
			hql.append(" and t.status =:status ");
		}else{
			params.remove("status");
		}
				
		return find(hql.toString(),params);
	}

	@Override
	public List<Teacher> findTeacherByStatus(int status) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select  t from Teacher as t ,Profession as p ,College as co ");		
		
		hql.append(" where p.college.id = co.id ");
		hql.append(" and t.profession.id =p.id ");
				
		hql.append(" and co.id =:collegeId ");
		hql.append(" and t.status =:status ");
		
		params.put("status", status);
		params.put("collegeId", academician.getCollege().getId());
				
		return find(hql.toString(),params);
	}
	
	
	
}
