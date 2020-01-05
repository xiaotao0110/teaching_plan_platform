package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IAdminDAO;
import com.hnieu.crtvn.entity.Admin;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class AdminDaoImpl extends BaseDao<Admin> implements IAdminDAO {

	@Override
	public List<Admin> findAdminByName(Admin admin) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", admin.getName());
		return find("from Admin as a where a.name=:name",params);
	}

}
