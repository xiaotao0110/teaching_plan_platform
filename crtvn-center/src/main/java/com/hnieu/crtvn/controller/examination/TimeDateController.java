package com.hnieu.crtvn.controller.examination;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hnieu.crtvn.service.IEtimeService;

@Controller
@RequestMapping(value = "timeDate")
public class TimeDateController {
	
	@Autowired
	private  HttpServletRequest request;
	
	@Autowired
	private IEtimeService etimeService;
	
	/**
	 * 时间段信息
	 */
	@RequestMapping(value = "timeDateInfo")
    public String courseInfoToView(Model model){	
		return "examination/timeDateInfo";
    }
	
	/**
	 * 时间段信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "timeDataGrid")
    public Map<String, Object> timeDataGrid(Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = etimeService.findEtimeByPage(pageNo, pageSize);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 添加时段
	 */
	@ResponseBody
	@RequestMapping(value = "addTime")
    public Map<String, Object> addTime(Timestamp stime,Timestamp etime,Model model){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			etimeService.insertEtime(stime,etime);
			map.put("msg", "ok");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "no");
			return map;
		}
    }
	
	/**
	 * 删除时段
	 */
	@ResponseBody
	@RequestMapping(value = "delTime")
    public Map<String, Object> delTime(int id, Model model){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			etimeService.deleteEtime(id);
			map.put("msg", "ok");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "no");
			return map;
		}
    }
	
	/**
	 *  时间信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "timeSelect")
    public List<Map<String, Object>> timeSelect(int mark,Model model){
		
		return etimeService.findEtime(mark);
    }
	
	
}
