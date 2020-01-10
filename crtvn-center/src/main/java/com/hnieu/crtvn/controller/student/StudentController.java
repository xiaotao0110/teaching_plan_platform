package com.hnieu.crtvn.controller.student;

import com.hnieu.crtvn.dto.CourseDTO;
import com.hnieu.crtvn.dto.StudentDTO;
import com.hnieu.crtvn.service.IStudentService;
import com.hnieu.crtvn.vo.StudentVO;
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
@RequestMapping(value = "student")
@SuppressWarnings("unchecked")
public class StudentController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IStudentService studentService;
	
	/**
	 * 学生信息
	 */
	@RequestMapping(value = "studentInfo")
    public String studentInfoToView(Model model){
		return "student/studentInfo";
    }
	
	/**
	 * 学生详情信息
	 */
	@RequestMapping(value = "studentDetails")
    public String studentDetailsToView(Model model, StudentDTO studentDTO){
		
		StudentVO studentVO = studentService.findStudentVOById(studentDTO);
		
		model.addAttribute("studentVO", studentVO);
		
		return "student/studentDetails";
    }
	
	/**
	 * 学生信息dataGrid
	 */
	@ResponseBody
	@RequestMapping(value = "studentDataGrid")
    public Map<String, Object> studentDataGrid(StudentDTO studentDTO, Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = studentService.findStudentByPage(pageNo, pageSize, studentDTO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 学生应考试下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "studentExamCourseYesSelect")
    public List<Map<String, Object>> studentExamCourseYesSelect(StudentDTO studentDTO, Model model){

		return studentService.findStudentByYesMark(studentDTO);
    }
	
	/**
	 * 学生非考试下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "studentExamCourseNoSelect")
    public List<Map<String, Object>> studentExamCourseNoSelect(StudentDTO studentDTO, Model model){

		return studentService.findStudentByNoMark(studentDTO);
    }
	
	
	
	/**
	 * 修改学生对应考试科目状态 
	 */
	@ResponseBody
	@RequestMapping(value = "studentExamCourseStatus")
    public Map<String, Object> studentExamCourseStatus(StudentDTO studentDTO, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			studentService.updateStudentCourse(studentDTO);
			map.put("msg", "ok");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "no");
			return map;
		}
    }
	
	/**
	 * 加载学生缺考理由
	 */
	@ResponseBody
	@RequestMapping(value = "studentReason")
    public Map<String, Object> studentReason(StudentDTO studentDTO, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String reason = studentService.findStudentselection(studentDTO);
		map.put("reason", reason);
		return map;
		
    }
	
	/**
	 * 统计该课程有多少学生
	 */
	@ResponseBody
	@RequestMapping(value = "studentCountbyCourse")
    public Map<String, Object> studentCountbyCourse(StudentDTO studentDTO, CourseDTO courseDTO , Model model){
		Map<String, Object> map = new HashMap<String, Object>();	
		long count = studentService.studentCountbyCourse(studentDTO);					
		map.put("count", count);
		
		//缺课人数
		long notExamcount = studentService.findNotExam(courseDTO);
		map.put("notExamcount", notExamcount);
		
		return map;		
    }
	
}
