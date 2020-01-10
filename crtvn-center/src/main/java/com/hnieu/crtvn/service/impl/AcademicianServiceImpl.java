package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.IAcademicianDAO;
import com.hnieu.crtvn.dao.ICollegeDAO;
import com.hnieu.crtvn.dao.impl.BaseDao;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.College;
import com.hnieu.crtvn.service.IAcademicianService;
import com.hnieu.crtvn.vo.AcademicianVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class AcademicianServiceImpl extends BaseDao<Academician> implements IAcademicianService {

	private static final int AC_ALL = 0; //0:表示全部
	
	@Autowired
	public IAcademicianDAO academicianDao;
	
	@Autowired
	private ICollegeDAO collegeDAO;

	@Override
	public Academician findAcademicianByName(Academician academician) {
		
		
		List<Academician> academicianBean = academicianDao.findAcademicianByName(academician);
		if(academicianBean.isEmpty()){
			return null;
		}
		for(int i = 0 ;i < academicianBean.size(); i++){
			if(academician.getName().equals(academicianBean.get(i).getName()) &&
					academician.getPassword().equals(academicianBean.get(i).getPassword())){
				return academicianBean.get(i) ;
			}
		}	
		return null;
	}

	@Override
	public Map<String, Object> findAcademicianByPage(int pageNo, int pageSize, AcademicianVO academicianVO) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(academicianVO.getCollegeId() != AC_ALL){			
			params.put("collegeId", academicianVO.getCollegeId());
		}
		
		List<AcademicianVO> resultList = academicianDao.findAcademicianByPage(pageNo, pageSize, params);
	
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",resultList.size());
		resultMap.put("rows", resultList);
        
		return resultMap;
	}

	@Override
	public void deleteAcademician(AcademicianVO academicianVO) {
		
		Academician academicianBean = academicianDao
				.findAcademicianById(academicianVO.getAcademicianId());
		
		academicianDao.deleteAcademician(academicianBean);
	}

	@Override
	public void addAcademician(AcademicianVO academicianVO) throws Exception{
		
		List<Academician> academicianList = academicianDao.findAcademician();
		Academician academicianBean = new Academician();
		
		for(int i=0 ; i < academicianList.size() ; i++){
			if(academicianVO.getAcademicianName().equals(academicianList.get(i).getName())){
				throw new Exception("名称已存在！");
			}		
		}
		academicianBean.setName(academicianVO.getAcademicianName());
		academicianBean.setPassword(academicianVO.getPassword());		
		
		College college = collegeDAO.findCollegeById(academicianVO.getCollegeId());
		academicianBean.setCollege(college);
		
		List<Integer> employeeNoList = new ArrayList<Integer>();
		for(int i=0 ; i < academicianList.size() ; i++){
			employeeNoList.add(Integer.parseInt(academicianList.get(i).getEmployeeNo()));
		}
		Collections.sort(employeeNoList);
		String code = String.valueOf(employeeNoList.get(employeeNoList.size()-1) + 1); 
		academicianBean.setEmployeeNo(code);
		
		
		academicianDao.addAcademician(academicianBean);
	}
	

}
