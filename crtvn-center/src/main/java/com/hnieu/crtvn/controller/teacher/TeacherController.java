package com.hnieu.crtvn.controller.teacher;

import com.hnieu.crtvn.dto.TeacherDTO;
import com.hnieu.crtvn.service.ITeacherService;
import com.hnieu.crtvn.vo.TeacherVO;
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
@RequestMapping(value = "teacher")
@SuppressWarnings("unchecked")
public class TeacherController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ITeacherService teacherService;
	
	/**
	 * 教师信息页面
	 */
	@RequestMapping(value = "teacherInfo")
    public String teacherInfoToView(Model model){
		return "teacher/teacherInfo";
    }
	
	/**
	 * 教师详情信息页面
	 */
	@RequestMapping(value = "teacherDetails")
    public String teacherDetailsToView(TeacherDTO teacherDTO, Model model){
		
		TeacherVO teacherVO = teacherService.findTeacherVOById(teacherDTO);
		
		model.addAttribute("teacherVO", teacherVO);
		
		return "teacher/teacherDetails";
    }
	
	/**
	 * 教师信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "teacherSelect")
    public List<Map<String, Object>> teacherSelect(Integer collegeId, Integer professionId, Model model){
				
		return teacherService.findTeacher(collegeId, professionId);
    }
	
	/**
	 * 教师信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "teacherDataGrid")
    public Map<String, Object> teacherDataGrid(TeacherDTO teacherDTO, Model model){
	    int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = teacherService.findTeacherByPage(pageNo, pageSize, teacherDTO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 修改监考状态
	 */
	@ResponseBody
	@RequestMapping(value = "teacherStatus")
    public Map<String, Object> teacherStatus(TeacherDTO teacherDTO, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			teacherService.updateTeacherManner(teacherDTO);
			map.put("msg", "ok");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "no");
			return map;
		}
    }
}
