package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.ICourseDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Course;
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
public class CourseDaoImpl extends BaseDao<Course> implements ICourseDAO {
	
	@Autowired
	private HttpServletRequest request;
	
	private static final int COURSE_ALL = 0; //0:表示全部
	
	@Override
	public List findCourseByPage(int pageNo, int pageSize, Map<String,Object> params) {
	
		return findByPage(getHql(params),pageNo,pageSize,params);
	}
	
	@Override
	public long findCourseCountByGrid(Map<String, Object> params) {

		return find(getHql(params),params).size(); 
	}

	@Override
	public void updateCourse(Course course) {
		update(course);
	}

	@Override
	public Course findCourseById(int id) {
		
		return get(Course.class, id);
	}

	@Override
	public List<Course> findCourseByPId(Map<String,Object> params) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		Course course = (Course)session.getAttribute("course");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct c from Course as c ,Profession as p ,College as co ");		
		hql.append(" where  co.id=p.college.id and  p.id=c.profession.id ");
		hql.append(" and co.id =:collegeId ");
		hql.append(" and c.startcd ="+"'"+course.getStartcd()+"'");
		
		int pid = Integer.parseInt(String.valueOf(params.get("professionId")));
		if(pid != COURSE_ALL){
			hql.append("and c.profession.id =:professionId ");
		}
		if(pid == COURSE_ALL){
			params.remove("professionId");
		}
			
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and c.mark=:mark ");
		}else{
			params.remove("mark");
		}
		
		if(!StringUtils.isEmpty(params.get("manner"))){
			hql.append(" and c.manner=:manner ");
		}else{
			params.remove("manner");
		}
		
		params.put("collegeId", academician.getCollege().getId());	

		
		return find(hql.toString(),params);
		
	}

	@Override
	public List findCourseByTId(Map<String, Object> params) {
		
		HttpSession session = request.getSession();
		Course course = (Course)session.getAttribute("course");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.CourseVO(c.id,c.name)  ");
		hql.append(" from College as co ,Profession as p , ");
		hql.append(" Teacher as t , Teach as tt ,Course as c  ");
		
		hql.append(" where  p.college.id=co.id and t.profession.id=p.id ");
		hql.append(" and tt.teacher.id=t.id and tt.course.id=c.id ");
		
		hql.append(" and co.id =:collegeId ");
		hql.append(" and p.id =:professionId ");
		hql.append(" and t.id =:teacherId ");

		hql.append(" and c.startcd ="+"'"+course.getStartcd()+"'");
		return find(hql.toString(),params);
	}

	@Override
	public long findCourseCount() {
		Map<String,Object> params = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		Course course = (Course)session.getAttribute("course");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select c ");		
		hql.append(" from Profession as p ,College as co , Course as c ");		
		hql.append(" where co.id=p.college.id ");
		hql.append(" and  c.profession.id=p.id ");
		
		hql.append(" and co.id =:collegeId ");
		hql.append(" and c.startcd ="+"'"+course.getStartcd()+"'");
		
		params.put("collegeId", academician.getCollege().getId());
		
		return find(hql.toString(),params).size();
	}

	private String getHql(Map<String,Object> params){
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		Course course = (Course)session.getAttribute("course");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.CourseVO("
				+ "  p.id, co.id, c.id, p.name, p.code,c.code, "
				+ "  c.name, c.nature, c.credit,c.manner,co.name, c.mark ,c.startcd"
				+ ") ");	
		hql.append(" from Course as c ,Profession as p ,College as co ");		
		hql.append(" where co.id=p.college.id and c.profession.id=p.id ");
		hql.append(" and  co.id=:collegeId ");
		hql.append(" and c.startcd ="+"'"+course.getStartcd()+"'");
		
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
		
		if(!StringUtils.isEmpty(params.get("courseId"))){
			hql.append(" and c.id =:courseId ");
		}else{
			params.remove("courseId");
		}
		
		if(!StringUtils.isEmpty(params.get("name"))){
			hql.append(" and c.name like :name");		
		}else{
			params.remove("name");
		}
		
		if(!StringUtils.isEmpty(params.get("manner"))){
			hql.append(" and c.manner =:manner ");
		}else{
			params.remove("manner");
		}
		
		if(!StringUtils.isEmpty(params.get("courseId"))){
			hql.append(" and c.id =:courseId ");
		}else{
			params.remove("courseId");
		}
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and c.mark =:mark ");
		}else{
			params.remove("mark");
		}
		
		
		params.put("collegeId", academician.getCollege().getId());
		
		return hql.toString();
	}

	@Override
	public long getMaxCourseId() {
		Map<String,Object> params = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select max(c.id) ");		
		hql.append(" from Profession as p ,College as co , Course as c ");		
		hql.append(" where co.id=p.college.id ");
		hql.append(" and  c.profession.id=p.id ");
		
		hql.append(" and co.id =:collegeId ");
				
		params.put("collegeId", academician.getCollege().getId());
		
		Integer id = (Integer) getMax(hql.toString(), params);
		return id;
	}

	@Override
	public List<Course> findCourseByMark(int mark) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select c ");	
		hql.append(" from Course as c ,Profession as p ,College as co ");		
		hql.append(" where co.id=p.college.id and c.profession.id=p.id ");
		hql.append(" and  co.id=:collegeId ");
		hql.append(" and  c.mark=:mark ");
		
		params.put("mark", mark);
		params.put("collegeId", academician.getCollege().getId());
				
		return find(hql.toString(), params);
	}
}
