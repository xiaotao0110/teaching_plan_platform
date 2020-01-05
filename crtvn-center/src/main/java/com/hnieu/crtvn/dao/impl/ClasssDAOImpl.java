package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IClasssDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Classs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class ClasssDAOImpl extends BaseDao<Classs> implements IClasssDAO {
  
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List findClasss(Map<String,Object> params) {

		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.ClasssVO("
				+ " co.id, co.name, p.id, p.name, t.id, t.name, cs.id, cs.code ) ");	
		
		hql.append(" from Profession as p ,College as co , Teacher as t, ");
		hql.append(" Course as c , Classselection as csl ,Classs as cs ");
		hql.append(" where  p.college.id=co.id ");
		hql.append(" and cs.teacher.id=t.id ");
		hql.append(" and cs.profession.id=p.id ");
		hql.append(" and csl.classs.id=cs.id ");
		hql.append(" and csl.course.id=c.id ");

		hql.append(" and co.id =:collegeId ");
		hql.append(" and p.id =:professionId ");
		hql.append(" and c.id =:courseId ");
			
		params.put("collegeId", academician.getCollege().getId());		
		
		return find(hql.toString(),params);
	}

	@Override
	public List getStudentCount(Map<String, Object> params) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.ClasssVO(cs.numbers)");			
		hql.append(" from Profession as p ,College as co ,");
		hql.append(" Course as c , Classselection as csl ,Classs as cs ");
		hql.append(" where  p.college.id=co.id ");
		hql.append(" and  c.profession.id=p.id ");
		hql.append(" and csl.classs.id=cs.id ");
		hql.append(" and csl.course.id=c.id ");

		hql.append(" and co.id =:collegeId ");
		hql.append(" and p.id =:professionId ");
		hql.append(" and c.id =:courseId ");
			
		params.put("collegeId", academician.getCollege().getId());		
		
		return find(hql.toString(),params);
	}

	@Override
	public List findClasssByPage(int pageNo, int pageSize, Map<String, Object> params) {
			
		return findByPage(getHql(params), pageNo, pageSize, params);
	}

	@Override
	public long findClasssCountByGrid(Map<String, Object> params) {

		return find(getHql(params), params).size();
	}
	
	@Override
	public List findClasssByPId(Map<String, Object> params) {

		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.ClasssVO( "
				+ "cs.id, cs.code"
				+ ") ");	
		
		hql.append(" from College as co , Profession as p ,Classs as cs ");

		hql.append(" where  p.college.id=co.id ");
		hql.append(" and cs.profession.id=p.id ");
		
		hql.append(" and co.id =:collegeId ");
			
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
		
		params.put("collegeId", academician.getCollege().getId());		
		
		return find(hql.toString(),params);
	}

	private String getHql(Map<String, Object> params){
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct new com.hnieu.crtvn.vo.ClasssVO("
				+ "co.id, co.name, p.id, p.name, t.id,t.name, cs.id,"
				+ " cs.code, cs.numbers"
				+ ") ");	
		
		hql.append(" from Profession as p ,College as co , Teacher as t,Classs as cs ");
		hql.append(" where  p.college.id=co.id ");	
		hql.append(" and cs.profession.id=p.id ");
		hql.append(" and cs.teacher.id=t.id ");
	
		hql.append(" and co.id =:collegeId ");
		
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
					
		params.put("collegeId", academician.getCollege().getId());		
		
		return hql.toString();
		
	}

	@Override
	public Classs findClasssById(Integer id) {

		return get(Classs.class, id);
	}

}
