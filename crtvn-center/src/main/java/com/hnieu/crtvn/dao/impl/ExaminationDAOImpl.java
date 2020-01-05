package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IExaminationDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Examination;
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
public class ExaminationDAOImpl extends BaseDao<Examination> implements IExaminationDAO {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void saveExamination(Examination examination) {
		save(examination);
	}

	@Override
	public List<Examination> findExaminationByMark(Map<String, Object> params) {

		return find(getHql2(params), params);
	}
	
	@Override
	public List<Examination> findExaminationByCourseId(Map<String, Object> params) {
		
		return find(getHql2(params), params);
	}

	private String getHql2(Map<String, Object> params){
		
		HttpSession session = request.getSession();
		Academician academician = (Academician) session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select ex from  Examination ex, College co , Profession p ");		
		hql.append(" where  p.college.id=co.id and  ex.profession.id = p.id  ");
			
		hql.append(" and co.id=:collegeId");
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and ex.mark=:mark ");
		}else{
			params.remove("mark");
		}
		
		if(!StringUtils.isEmpty(params.get("courseId"))){
			hql.append(" and ex.course.id=:courseId ");
		}else{
			params.remove("courseId");
		}		
		
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
		
		hql.append(" order by ex.id asc ");	
		
		params.put("collegeId", academician.getCollege().getId());
		
		return hql.toString();
		
	}
	
	@Override
	public List<Examination> findExaminationByPage(int pageNo, int pageSize, Map<String, Object> params) {
				
		return findByPageAndSQL(getHql(params), pageNo, pageSize, params, Examination.class);
	}

	@Override
	public List<Examination> findExamination(Map<String, Object> params) {

		return findBySQL(getHql(params), params, Examination.class);
	}
	
	@Override
	public long findExaminationCountByGrid(Map<String, Object> params) {

		return findCountByPageAndSQL(getHql(params),params, Examination.class).size();
	}

	private String getHql(Map<String, Object> params){
		StringBuffer hql = new StringBuffer();
		hql.append(" select ex.*  ");		
		hql.append(" from college co LEFT JOIN  profession p  on p.cid=co.id ");
		hql.append(" LEFT JOIN examination ex on ex.pid = p.id  ");			
		hql.append(" LEFT JOIN teacher t on t.id = ex.ONETID or t.id = ex.TWOTID   ");
		hql.append(" LEFT JOIN course  c on c.id = ex.coid ");
		
		hql.append(" where 1=1 ");
		hql.append(" and co.id =:collegeId ");
		hql.append(" and co.id is not null ");
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and ex.mark=:mark ");
		}else{
			params.remove("mark");
		}
		
		if(!StringUtils.isEmpty(params.get("professionId"))){
			hql.append(" and p.id=:professionId ");
		}else{
			params.remove("professionId");
		}
		
		if(!StringUtils.isEmpty(params.get("courseId"))){
			hql.append(" and ex.coid=:courseId ");
		}else{
			params.remove("courseId");
		}
		
		if(!StringUtils.isEmpty(params.get("teacherId1"))){
			hql.append(" and ex.ONETID=:teacherId1 ");
		}else{
			params.remove("teacherId1");
		}
		
		if(!StringUtils.isEmpty(params.get("teacherId2"))){
			hql.append(" and ex.TWOTID=:teacherId2 ");
		}else{
			params.remove("teacherId2");
		}
		
		hql.append(" GROUP BY ex.id ");
		
		return hql.toString();
	}

	@Override
	public Examination findExaminationById(int id) {

		return get(Examination.class, id);
	}

	@Override
	public void updateExamination(Examination examination) {

		update(examination);
	}

	@Override
	public void deleteExamination(Examination examination) {

		delete(examination);
	}

}
