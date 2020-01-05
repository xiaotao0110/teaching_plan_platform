package com.hnieu.crtvn.controller.classs;

import com.hnieu.crtvn.dto.ClasssDTO;
import com.hnieu.crtvn.service.IClasssService;
import com.hnieu.crtvn.vo.ClasssVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "classs")
public class ClasssController {
	
	@Autowired
	private IClasssService classsService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 班级信息
	 */
	@RequestMapping(value = "classsInfo")
    public String classsInfoToView(Model model){
		return "classs/classsInfo";
    }
	
	/**
	 * 班级详情信息
	 */
	@RequestMapping(value = "classsDetails")
    public String classsDetailsToView(ClasssDTO classsDTO, Model model){
		
		ClasssVO classsVO = classsService.findClasssVOById(classsDTO);
				
		model.addAttribute("classsVO", classsVO);
		
		return "classs/classsDetails";
    }
	
	/**
	 * 教学班号信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "classsSelect")
    public List<Map<String, Object>> classsSelect(ClasssDTO classsDTO, Model model){

		return classsService.findClasss(classsDTO);
    }
	
	/**
	 * 学生模块   教学班号信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "classsSelect2")
    public List<Map<String, Object>> classsSelect2(ClasssDTO classsDTO, Model model){

		return classsService.findClasssByPId(classsDTO);
    }
	
	
	/**
	 * 班级信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "classsDataGrid")
    public Map<String, Object> classsDataGrid(ClasssDTO classsDTO, Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = classsService.findClasssByPage(pageNo, pageSize, classsDTO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }

}
