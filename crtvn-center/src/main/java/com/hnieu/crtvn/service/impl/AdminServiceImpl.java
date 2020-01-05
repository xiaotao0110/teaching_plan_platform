package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.IAdminDAO;
import com.hnieu.crtvn.dao.impl.BaseDao;
import com.hnieu.crtvn.entity.Admin;
import com.hnieu.crtvn.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl extends BaseDao<Admin> implements IAdminService {

	@Autowired
	public IAdminDAO adminDao;

	@Override
	public Admin findAdminByName(Admin admin) {
		
		List<Admin> adminBean = adminDao.findAdminByName(admin);
		if(adminBean.isEmpty()){
			return null;
		}
		for(int i = 0 ;i < adminBean.size(); i++){
			if(admin.getName().equals(adminBean.get(i).getName()) && 
					admin.getPassword().equals(adminBean.get(i).getPassword())){				
				return adminBean.get(i) ;
			}
		}
		return null;	
	}

}
