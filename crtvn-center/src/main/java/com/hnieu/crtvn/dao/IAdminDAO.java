package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Admin;

import java.util.List;

public interface IAdminDAO {
	
	 List<Admin> findAdminByName(Admin admin);  
        
}
