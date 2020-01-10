package com.hnieu.crtvn.controller.admin;

import com.hnieu.crtvn.service.IAcademicianService;
import com.hnieu.crtvn.vo.AcademicianVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "admin")
@SuppressWarnings("unchecked")
public class AdminController {
	
	@Autowired
	private IAcademicianService academicianService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 教务人员信息
	 */
	@RequestMapping(value = "adminInfo")
    public String adminInfoToView(Model model){
		
		return "admin/adminInfo";
    }
	
	
	/**
	 * 教务人员信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "academicianDataGrid")
    public Map<String, Object> adminDataGrid(AcademicianVO academicianVO, Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = academicianService.findAcademicianByPage(pageNo, pageSize, academicianVO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 添加教务人员
	 */
	@ResponseBody
	@RequestMapping(value = "addAcademician")
    public Map<String, Object> addAdmin(AcademicianVO academicianVO, Model model){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			academicianService.addAcademician(academicianVO);
			map.put("msg", "添加成功");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "添加失败"+e.getMessage());
			return map;
		}
    }
	

	/**
	 *删除教务人员
	 */
	@RequestMapping(value = "deleteAcademician")
    public String deleteAdmin(AcademicianVO academicianVO, Model model){
		
		try{
			academicianService.deleteAcademician(academicianVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/admin/adminInfo";
    }
}
