package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IEtimeDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Etime;
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
public class EtimeDAOImpl extends BaseDao<Etime> implements IEtimeDAO {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void insertEtime(Etime etime) {
		
		update(etime);
	}

	@Override
	public void deleteEtime(Etime etime) {

		delete(etime);
	}

	@Override
	public void updateEtime(Etime etime) {

		update(etime);
	}

	@Override
	public Etime findEtimeById(int id) {

		return get(Etime.class, id);
	}

	@Override
	public List<Etime> findEtimeByPage(int pageNo, int pageSize) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		params.put("collegeId", academician.getCollege().getId());

		return findByPage(getHql(params),pageNo,pageSize,params);
	}

	@Override
	public long findEtimeCountByGrid() {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		params.put("collegeId", academician.getCollege().getId());

		return find(getHql(params),params).size();
	}

	@Override
	public List<Etime> findEtime(Map<String,Object> params) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		params.put("collegeId", academician.getCollege().getId());

		return find(getHql(params),params);
	}
	
	private String getHql(Map<String,Object> params){
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select e from Etime as e ");		
			
		hql.append(" where e.college.id =:collegeId ");	
		
		if(!StringUtils.isEmpty(params.get("mark"))){
			hql.append(" and  e.mark=:mark ");
		}else{
			params.remove("mark");
		}
		
		hql.append(" ORDER BY e.stime asc");		
		
		return  hql.toString();
		
	}

	@Override
	public List<Etime> findEtimeByMark(int mark) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select e from Etime as e ");		
			
		hql.append(" where e.college.id =:collegeId ");	
		hql.append(" and e.mark=:mark ");	
		
		params.put("mark", mark);
		params.put("collegeId", academician.getCollege().getId());

		return find(hql.toString(), params);
	}
}
