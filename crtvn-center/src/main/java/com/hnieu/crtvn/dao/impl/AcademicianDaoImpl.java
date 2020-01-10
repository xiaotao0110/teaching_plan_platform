package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IAcademicianDAO;
import com.hnieu.crtvn.entity.Academician;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class AcademicianDaoImpl extends BaseDao<Academician> implements IAcademicianDAO {

	@Override
	public List<Academician> findAcademicianByName(Academician academician) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", academician.getName());
		return find("from Academician as a where a.name =:name", params);
	}

	@Override
	public List findAcademicianByPage(int pageNo, int pageSize, Map<String, Object> params) {

		StringBuffer hql = new StringBuffer();
		hql.append(" select  distinct new  com.hnieu.crtvn.vo.AcademicianVO("
				+ "a.id, a.name, co.id, co.name,a.employeeNo,a.password"
				+ ") ");		
		hql.append(" from College as co ,Academician as a ");	
		hql.append(" where a.college.id = co.id ");	
		
		if(!StringUtils.isEmpty(params.get("collegeId"))){
			hql.append(" and  co.id=:collegeId ");
		}else{
			params.remove("collegeId");
		}	
		
		return findByPage(hql.toString(), pageNo, pageSize, params);
	}

	@Override
	public void deleteAcademician(Academician academician) {
		
		delete(academician);
	}

	@Override
	public void addAcademician(Academician academician) {
		
		save(academician);
	}

	@Override
	public Academician findAcademicianById(int id) {

		return get(Academician.class, id);
	}

	@Override
	public List<Academician> findAcademician() {

		return findAll(Academician.class);
	}

}
