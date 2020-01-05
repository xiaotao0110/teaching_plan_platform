package com.hnieu.crtvn.controller.profession;

import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.service.IProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "profession")
public class ProfessionController {
	
	@Autowired
	private IProfessionService professionService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 专业信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "professionSelect")
    public List<Map<String,Object>> professionSelect(Integer collegeId, Model model){
			
		if(collegeId==null){
			HttpSession session = request.getSession();
			Academician academician = (Academician)session.getAttribute("academician");
			
			return professionService.findProfession(academician.getCollege().getId());
		}else{
			
			return professionService.findProfession(collegeId);
		}
		
    }
	
	/**
	 * 专业信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "professionDataGrid")
    public Map<String, Object> professionDataGrid(Model model){
		
	    int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = professionService.findProfessionByPage(pageNo, pageSize);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
}
