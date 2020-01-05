package com.hnieu.crtvn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hnieu.crtvn.dao.IClasssDAO;
import com.hnieu.crtvn.dao.impl.BaseDao;
import com.hnieu.crtvn.dto.ClasssDTO;
import com.hnieu.crtvn.entity.Classs;
import com.hnieu.crtvn.service.IClasssService;
import com.hnieu.crtvn.vo.ClasssVO;
import com.hnieu.crtvn.vo.CourseVO;

@Service
@Transactional
public class ClasssServiceImpl extends BaseDao<Classs> implements IClasssService {

	private static final int CLASSS_ALL = 0; //0:表示全部
	
	@Autowired
	private  IClasssDAO classsDAO;
	
	@Override
	public List<Map<String,Object>> findClasss(ClasssDTO classsDTO) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("professionId", classsDTO.getProfessionId());
		params.put("courseId", classsDTO.getCourseId());	
		
		List<ClasssVO>  classsList = classsDAO.findClasss(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> classsMap = new HashMap<String,Object>();
	
		for(int i=0; i < classsList.size(); i++){	
			classsMap = new HashMap<String,Object>();
			classsMap.put("text",classsList.get(i).getClasssCode());
			classsMap.put("id",classsList.get(i).getClasssId());
			list.add(classsMap);
	    }		
		return list;
	}

	@Override
	public int getStudentCount(ClasssDTO classsDTO) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("professionId", classsDTO.getProfessionId());
		params.put("courseId", classsDTO.getCourseId());
		
		List<ClasssVO> list = classsDAO.getStudentCount(params);
		int numbers = 0;
		for(int i=0; i<list.size();i++){
			numbers += list.get(i).getNumbers();
		}
		
		return numbers;
	}

	@Override
	public Map<String, Object> findClasssByPage(int pageNo, int pageSize, ClasssDTO classsDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(classsDTO.getProfessionId()!=CLASSS_ALL){
			params.put("professionId", classsDTO.getProfessionId());
		}
			
		List<ClasssVO> resultList = classsDAO.findClasssByPage(pageNo, pageSize, params);
	
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",classsDAO.findClasssCountByGrid(params));
		resultMap.put("rows", resultList);
        
		return resultMap;
	}

	@Override
	public ClasssVO findClasssVOById(ClasssDTO classsDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("professionId", classsDTO.getProfessionId());
		
		List<ClasssVO>  classsVOList = classsDAO.findClasssByPage(1, 1, params);
				
		return classsVOList.get(0);
	}

	@Override
	public List<Map<String, Object>> findClasssByPId(ClasssDTO classsDTO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(classsDTO.getProfessionId()!=CLASSS_ALL){
			params.put("professionId", classsDTO.getProfessionId());
		}

		List<ClasssVO>  classsList = classsDAO.findClasssByPId(params);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> classsMap = new HashMap<String,Object>();
	
		classsMap.put("text","全部");
		classsMap.put("id",0);
		list.add(classsMap);
		
		for(int i=0; i < classsList.size(); i++){	
			classsMap = new HashMap<String,Object>();
			classsMap.put("text",classsList.get(i).getClasssCode());
			classsMap.put("id",classsList.get(i).getClasssId());
			list.add(classsMap);
	    }		
		return list;
	}

	
}
