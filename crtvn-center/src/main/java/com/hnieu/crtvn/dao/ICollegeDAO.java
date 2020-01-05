package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.College;

import java.util.List;

public interface ICollegeDAO {
	
	List<College> findCollege();
	
	College findCollegeById(int id);
	
}
