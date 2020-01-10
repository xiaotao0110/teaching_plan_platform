package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.ICollegeDAO;
import com.hnieu.crtvn.dao.IEtimeDAO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Etime;
import com.hnieu.crtvn.service.IEtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class EtimeServiceImpl implements IEtimeService {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IEtimeDAO etimeDAO;
	
	@Autowired
	private ICollegeDAO collegeDAO;
	
	@Override
	public void insertEtime(Timestamp stime,Timestamp etime) {
		
		HttpSession session = request.getSession();
		Academician academician = (Academician)session.getAttribute("academician");

		Etime etimeBean = new Etime();
		etimeBean.setStime(stime);
		etimeBean.setEtime(etime);
		
		etimeBean.setCollege(collegeDAO.findCollegeById(academician.getCollege().getId()));
		
		etimeDAO.insertEtime(etimeBean);
	}

	@Override
	public void deleteEtime(int id) {

		Etime etimeBean = etimeDAO.findEtimeById(id);
		
		etimeDAO.deleteEtime(etimeBean);
	}

	@Override
	public Map<String, Object> findEtimeByPage(int pageNo, int pageSize) {
				
		List<Etime> resultList = etimeDAO.findEtimeByPage(pageNo, pageSize);
		Collections.sort(resultList, new Comparator<Etime>() {
            public int compare(Etime arg0, Etime arg1) {
                long hits0 = arg0.getEtime().getTime();
                long hits1 = arg1.getEtime().getTime();
                if (hits1 < hits0) {
                    return 1;
                } else if (hits1 == hits0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",etimeDAO.findEtimeCountByGrid());
		resultMap.put("rows", resultList);
        
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> findEtime(int mark) {
		Map<String,Object> params = new HashMap<String,Object>();		
		
		params.put("mark", mark);		
		
		List<Etime>  etimes = etimeDAO.findEtimeByMark(mark);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> etimeMap = new HashMap<String,Object>();
		
		etimeMap.put("text","全部");
		etimeMap.put("id",0);
		list.add(etimeMap);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StringBuffer timeStr = null;
		Etime etimeBean = null;
		for(int i=0; i < etimes.size(); i++){
			timeStr = new StringBuffer();
			etimeBean = etimes.get(i);
			etimeMap = new HashMap<String,Object>();
			Date stime = etimeBean.getStime();
			Date etime = etimeBean.getEtime();
			timeStr.append(sdf.format(stime)).append("--").append(sdf.format(etime));
			etimeMap.put("text",timeStr.toString());
			etimeMap.put("id",etimes.get(i).getId());
			
			list.add(etimeMap);
	    }		
		return list;
	}

}
