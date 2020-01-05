package com.hnieu.crtvn.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hnieu.crtvn.dao.ICollegeDAO;
import com.hnieu.crtvn.entity.College;

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
