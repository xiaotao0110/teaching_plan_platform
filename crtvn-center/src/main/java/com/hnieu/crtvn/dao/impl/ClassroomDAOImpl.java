package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IClassroomDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Classroom;
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
public class ClassroomDAOImpl extends BaseDao<Classroom> implements IClassroomDAO {

	@Autowired
	private HttpServletRequest request;

	@Override
	public List<Classroom> findClassroomByPId(Map<String, Object> params) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct cm from College as co ,Classroom as cm ");		
		
		hql.append(" where cm.college.id = co.id ");			
		if(!StringUtils.isEmpty(params.get("collegeId"))){
			hql.append(" and  co.id=:collegeId ");
			hql.append(" group by  cm.building ");
		}else{
			params.remove("collegeId");
			hql.append(" group by  cm.building ");
		}	
				
		return find(hql.toString(),params);

	}

	@Override
	public List findClassroomByPage(int pageNo, int pageSize, Map<String, Object> params) {
		
		return findByPage(getHql(params), pageNo, pageSize, params);
	}

	@Override
	public long findClassroomCountByGrid(Map<String, Object> params) {

		return find(getHql(params), params).size();
	}
	
	@Override
	public void updateClassroom(Classroom classroom) {
		update(classroom);
		flush();
	}

	@Override
	public Classroom findClassroomById(int id) {

		return get(Classroom.class, id);
	}

	@Override
	public List<Classroom> findClassroomSeatsCount(Map<String, Object> params) {

		StringBuffer hql = new StringBuffer();
		hql.append(" select cm ");		
		hql.append(" from College as co ,Classroom as cm ");	
		hql.append(" where cm.college.id = co.id ");	
		
		hql.append(" and  co.id=:collegeId ");
		hql.append(" and  cm.mark=:mark ");
		hql.append(" and  cm.status=:status ");
		
		hql.append(" ORDER BY cm.number asc");	
		
		 return find(hql.toString(), params);
	}
	
	@Override
	public long getMaxClassroomId() {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
			
		Map<String, Object> params = new HashMap<String, Object>();	
		StringBuffer hql = new StringBuffer();
		hql.append(" select max(cm.id) ");		
		hql.append(" from College as co ,Classroom as cm ");	
		hql.append(" where cm.college.id = co.id ");	
		
		hql.append(" and  co.id=:collegeId ");
		
		params.put("collegeId", academician.getCollege().getId());		
		
		Integer id = (Integer) getMax(hql.toString(), params);
		
		return id;
	}
	
	private String getHql(Map<String, Object> params){

		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.ClassroomVO("
				+ "cm.id, co.id, cm.code,cm.status, cm.building, cm.seats, cm.number, cm.mark,co.name "
				+ ") ");		
		hql.append(" from College as co ,Classroom as cm ");	
		hql.append(" where cm.college.id = co.id ");	
		
		if(!StringUtils.isEmpty(params.get("collegeId"))){
			hql.append(" and  co.id=:collegeId ");
		}else{
			params.remove("collegeId");
		}	
		
		if(!StringUtils.isEmpty(params.get("building"))){
			hql.append(" and  cm.building=:building ");
		}else{
			params.remove("building");
		}	
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and  cm.mark=:mark ");
		}else{
			params.remove("mark");
		}	
		
		if(!StringUtils.isEmpty(params.get("status"))){
			hql.append(" and  cm.status=:status ");
		}else{
			params.remove("status");
		}	
		
		if(!StringUtils.isEmpty(params.get("classroomId"))){
			hql.append(" and  cm.id=:classroomId ");
		}else{
			params.remove("classroomId");
		}
		
		hql.append(" ORDER BY cm.number asc");	
		return hql.toString();
	}

	@Override
	public List<Classroom> findClassroomByMark(int status) {
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
			
		Map<String, Object> params = new HashMap<String, Object>();	
		StringBuffer hql = new StringBuffer();
		hql.append(" select cm ");		
		hql.append(" from College as co ,Classroom as cm ");	
		hql.append(" where cm.college.id = co.id ");	
		
		hql.append(" and co.id=:collegeId ");
		
		hql.append(" and cm.status=:status ");
		
		params.put("collegeId", academician.getCollege().getId());		
		params.put("status", status);
		
		return find(hql.toString(), params);
	}

}
