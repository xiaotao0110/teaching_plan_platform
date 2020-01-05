package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.ICollegeDAO;
import com.hnieu.crtvn.entity.College;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class CollegeDAOImpl extends BaseDao<College> implements ICollegeDAO {

	@Override
	public List<College> findCollege() {

		return findAll(College.class);
	}

	@Override
	public College findCollegeById(int id) {

		return get(College.class, id);
	}

}
