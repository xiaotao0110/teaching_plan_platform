package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IProfessionDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class ProfessionDAOImpl extends BaseDao<Profession> implements IProfessionDAO {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List findProfessionByPage(int pageNo, int pageSize,Map<String,Object> params) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		StringBuffer hql = new StringBuffer();
		hql.append(" select new com.hnieu.crtvn.vo.ProfessionVO("
				+ " co.id, co.name,p.id, p.name,p.code,p.departmentHead, p.secretary "
				+ ") ");
		
		hql.append(" from Profession as p ,College co ");		
		hql.append(" where p.college.id=co.id ");
		hql.append(" and co.id =:collegeId ");
		
		params.put("collegeId", academician.getCollege().getId());
		
		return findByPage(hql.toString(), pageNo, pageSize,params);
	}

	@Override
	public List<Profession> findProfession(Map<String,Object> params) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select p ");		
		hql.append(" from Profession as p ,College as co ");		
		hql.append(" where co.id=p.college.id ");
		
		if((Integer)params.get("collegeId") != 0){
			hql.append(" and co.id =:collegeId ");
		}else{
			params.remove("collegeId");
		}
						
		return find(hql.toString(),params);
	}

	@Override
	public long findProfessionCount() {
		
		Map<String,Object> params = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		StringBuffer hql = new StringBuffer();
		hql.append(" select p ");		
		hql.append(" from Profession as p ,College as co ");		
		hql.append(" where co.id=p.college.id ");
		
		hql.append(" and co.id =:collegeId ");
				
		params.put("collegeId", academician.getCollege().getId());
		
		return find(hql.toString(),params).size();
	}

	@Override
	public Profession findProfessionByCourseId(Map<String, Object> params) {

		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		StringBuffer hql = new StringBuffer();
		hql.append(" select p ");		
		hql.append(" from Profession as p ,College as co , Course as c ");		
		hql.append(" where co.id=p.college.id ");
		hql.append(" and  c.profession.id=p.id ");
		
		hql.append(" and co.id =:collegeId ");
				
		params.put("collegeId", academician.getCollege().getId());
		
		if((Integer)params.get("courseId") != 0){
			hql.append(" and c.id =:courseId ");
		}else{
			params.remove("courseId");
		}
		
		return find(hql.toString(),params).get(0);
	}

}
