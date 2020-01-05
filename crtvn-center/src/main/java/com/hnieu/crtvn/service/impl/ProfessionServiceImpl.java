package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.IProfessionDAO;
import com.hnieu.crtvn.entity.Profession;
import com.hnieu.crtvn.service.IProfessionService;
import com.hnieu.crtvn.vo.ProfessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProfessionServiceImpl implements IProfessionService {

	@Autowired
	public IProfessionDAO professionDAO;
	
	
	@Override
	public Map<String, Object> findProfessionByPage(int pageNo, int pageSize) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		List<ProfessionVO>  resultList = professionDAO.findProfessionByPage(pageNo, pageSize, params);
		
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",resultList.size());
		resultMap.put("rows", resultList);
        
		return resultMap;
		
	}

	@Override
	public List<Map<String,Object>> findProfession(int collegeId) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("collegeId", collegeId);
		List<Profession>  professionList = professionDAO.findProfession(params);
		
	    return  getProfessionReslut(professionList);
	}
	
	private List<Map<String,Object>> getProfessionReslut(List<Profession>  professionList){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object>  professionMap = new HashMap<String,Object>();
		
		professionMap.put("text","全部");
		professionMap.put("id",0);
		list.add(professionMap);
		
		for(int i=0; i < professionList.size(); i++){	
			professionMap = new HashMap<String,Object>();
		    professionMap.put("text",professionList.get(i).getName());
		    professionMap.put("id",professionList.get(i).getId());
		    list.add(professionMap);
	    }
	    return  list;
	}

	@Override
	public long findProfessionCount() {
		
		return professionDAO.findProfessionCount();
	}
}
