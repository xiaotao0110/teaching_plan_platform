package com.hnieu.crtvn.dao;

import java.util.List;

import com.hnieu.crtvn.entity.College;

public interface ICollegeDAO {
	
	List<College> findCollege();
	
	College findCollegeById(int id);
	
}
