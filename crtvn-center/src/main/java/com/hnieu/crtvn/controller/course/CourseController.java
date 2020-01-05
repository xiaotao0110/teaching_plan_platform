package com.hnieu.crtvn.controller.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hnieu.crtvn.dto.ClasssDTO;
import com.hnieu.crtvn.dto.CourseDTO;
import com.hnieu.crtvn.service.IClasssService;
import com.hnieu.crtvn.service.ICourseService;
import com.hnieu.crtvn.vo.CourseVO;

@Controller
@RequestMapping(value = "course")
public class CourseController {
	
	@Autowired
	public ICourseService courseService;
	
	@Autowired
	private IClasssService classsService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 课程信息页面
	 */
	@RequestMapping(value = "courseInfo")
    public String courseInfoToView(Model model){	
		
		return "course/courseInfo";
    }
	
	/**
	 * 课程详情信息页面
	 */
	@RequestMapping(value = "courseDetails")
    public String courseDetailsToView(CourseDTO courseDTO,Model model){
		
		CourseVO courseVO = courseService.findCourseVOById(courseDTO);
		
		ClasssDTO classsDTO = new ClasssDTO();
		classsDTO.setProfessionId(courseDTO.getProfessionId());
		classsDTO.setCourseId(courseDTO.getCourseId());
		courseVO.setNumbers(classsService.getStudentCount(classsDTO));
		
		model.addAttribute("courseVO", courseVO);
		
		return "course/courseDetails";
    }
	
	/**
	 * 课程信息下拉框 课程模块
	 */
	@ResponseBody
	@RequestMapping(value = "courseSelect")
    public List<Map<String, Object>> courseSelect(CourseDTO courseDTO, Model model){
		
		return courseService.findCourse(courseDTO);
    }
	
	/**
	 * 课程信息下拉框 教师模块
	 */
	@ResponseBody
	@RequestMapping(value = "courseSelect2")
    public List<Map<String, Object>> courseSelect2(CourseDTO courseDTO, Model model){
		
		return courseService.findCourseByTId(courseDTO);
    }
	
	/**
	 * 课程信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "courseDataGrid")
    public Map<String, Object> courseDataGrid(CourseDTO courseDTO,Model model){
		
	    int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = courseService.findCourseByPage(pageNo,pageSize, courseDTO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 修改考试形式
	 */
	@ResponseBody
	@RequestMapping(value = "courseStatus")
    public Map<String, Object> courseStatus(CourseDTO courseDTO,Model model){	
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			courseService.updateCourseManner(courseDTO);
			map.put("msg", "ok");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "no");
			return map;
		}
    }
	
	/**
	 * 修改考试是否安排
	 */
	@ResponseBody
	@RequestMapping(value = "delCourse")
    public Map<String, Object> delCourse(CourseDTO courseDTO,Model model){	
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			courseService.updateCourseMark(courseDTO);
			map.put("msg", "删除成功");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "删除失败");
			return map;
		}
    }
	
}
