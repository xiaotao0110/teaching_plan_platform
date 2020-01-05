package com.hnieu.crtvn.dao;

import java.util.List;

import com.hnieu.crtvn.entity.Admin;

public interface IAdminDAO {
	
	 List<Admin> findAdminByName(Admin admin);  
        
}
