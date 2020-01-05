package com.hnieu.crtvn.controller.classroom;

import com.hnieu.crtvn.dto.ClassroomDTO;
import com.hnieu.crtvn.service.IClassroomService;
import com.hnieu.crtvn.vo.ClassroomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "classroom")
public class ClassroomController {
	
	@Autowired
	private IClassroomService classroomService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 教室信息
	 */
	@RequestMapping(value = "classroomInfo")
    public String classroomInfoToView(Model model){
		return "classroom/classroomInfo";
    }
	
	/**
	 * 教室详情信息
	 */
	@RequestMapping(value = "classroomDetails")
    public String classroomDetailsToView(ClassroomDTO classroomDTO, Model model){
		
		ClassroomVO classroomVO = classroomService.findClassroomVOById(classroomDTO);
		
		model.addAttribute("classroomVO", classroomVO);
		
		return "classroom/classroomDetails";
    }
	
	/**
	 * 楼栋信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "BuildingSelect")
    public List<Map<String, Object>> BuildingSelect(ClassroomDTO classroomDTO, Model model){
		
		return classroomService.findClassroom(classroomDTO);
    }
	
	
	/**
	 * 教室信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "classroomDataGrid")
    public Map<String, Object> classroomDataGrid(ClassroomDTO classroomDTO, Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = classroomService.findClassroomByPage(pageNo, pageSize, classroomDTO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 修改考场状态
	 */
	@ResponseBody
	@RequestMapping(value = "classroomStatus")
    public Map<String, Object> classroomStatus(ClassroomDTO classroomDTO, Model model){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			classroomService.updateClassroomManner(classroomDTO);
			map.put("msg", "ok");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "no");
			return map;
		}
    }
}
