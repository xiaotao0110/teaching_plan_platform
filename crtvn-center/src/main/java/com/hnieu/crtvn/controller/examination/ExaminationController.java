package com.hnieu.crtvn.controller.examination;

import com.hnieu.crtvn.dto.ExamDTO;
import com.hnieu.crtvn.dto.ExaminationDTO;
import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.service.IExamService;
import com.hnieu.crtvn.service.IExaminationService;
import com.hnieu.crtvn.util.ExportExcelUtil;
import com.hnieu.crtvn.util.ExportExcelWrapper;
import com.hnieu.crtvn.util.Reduction;
import com.hnieu.crtvn.vo.DownloadExamVO;
import com.hnieu.crtvn.vo.DownloadExaminationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "examination")
public class ExaminationController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IExaminationService examinationService;
	
	@Autowired
	private IExamService examService;
	
	@Autowired
	private Reduction reduction;
	
	/**
	 * 自动排考页面 
	 */
	@RequestMapping(value = "automatic")
    public String automaticToView(Model model){
		return "examination/automatic";
    }
	
	
	/**
	 * 生成考场信息
	 */
	@ResponseBody
	@RequestMapping(value = "automaticInfo")
    public Map<String, Object> automaticInfo(ExaminationDTO examinationDTO, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Map<String,String> result = examinationService.automatic(examinationDTO);					
			map.put("msg", result.get("msg"));
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "生成考场信息失败");
			return map;
		}
    }
	
	
	
	/**
	 * 手动安排考试
	 */
	@RequestMapping(value = "manual")
    public String manualToView(Model model){
		HttpSession session = request.getSession();
		Academician academician = (Academician) session.getAttribute("academician");
		
		model.addAttribute("collegeId", academician.getCollege().getId());
		return "examination/manual";
    }
	
	/**
	 * 生成考试信息
	 */
	@ResponseBody
	@RequestMapping(value = "exam")
    public Map<String, Object> exam(ExamDTO examDTO, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Map<String,String> result = examService.exam(examDTO);					
			map.put("msg", result.get("msg"));
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "生成考试信息失败");
			return map;
		}
    }
		

	/**
	 * 考场信息dataGrid -- 以标记区分自动和手动排考
	 */
	@ResponseBody
	@RequestMapping(value = "examinationDataGrid")
    public Map<String, Object> examinationDataGrid(ExaminationDTO examinationDTO, Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = examinationService.findExaminationByPage(pageNo, pageSize, examinationDTO);
		
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	
	/**
	 * 下载考场
	 */
	@ResponseBody
	@RequestMapping(value = "exportExcelExamination")
    public void exportExcelExamination(ExaminationDTO examinationDTO, HttpServletResponse response){
		List<DownloadExaminationVO> downloadExaminationVOs
					= examinationService.exportExcelExamination(examinationDTO);
		
		//表头
		String[] columnNames = { "考场号", "专业", " 课程代码" ,"课程名称", 
				"教室" ,"监考人员一" ,"监考人员二" ,"开始时段" ,"结束时段", "人数"};
			
		
		String fileName = "考场信息";
		
		ExportExcelWrapper<DownloadExaminationVO> excelWrapper =
							new ExportExcelWrapper<DownloadExaminationVO>();
		excelWrapper.exportExcel(fileName, fileName, columnNames, downloadExaminationVOs, 
							response, ExportExcelUtil.EXCEL_FILE_2003);
    }
	
	/**
	 * 下载考试
	 */
	@ResponseBody
	@RequestMapping(value = "exportExcelExamn")
    public void exportExcelExamn(ExaminationDTO examinationDTO, HttpServletResponse response){
		List<DownloadExamVO> downloadExamVOs
					= examinationService.exportExcelExam(examinationDTO);
		
		//表头
		String[] columnNames = { "学号", "姓名", " 班级" ,"课程", "考场号" ,"考试时间" ,"座位号" };
			
		
		String fileName = "考试信息";
		
		ExportExcelWrapper<DownloadExamVO> excelWrapper =
							new ExportExcelWrapper<DownloadExamVO>();
		excelWrapper.exportExcel(fileName, fileName, columnNames, downloadExamVOs, 
							response, ExportExcelUtil.EXCEL_FILE_2003);
	
    }
	
	
	/**
	 * 监考老师下拉框 -- 以标记区分自动和手动排考  查询条件
	 */
	@ResponseBody
	@RequestMapping(value = "examTeacherSelect")
    public List<Map<String, Object>> examTeacherSelect(ExaminationDTO examinationDTO, Model model){
		
		return examinationService.findExamTeacherSelect(examinationDTO);
    }
	
	/**
	 * 考试信息页面
	 */
	@RequestMapping(value = "examinationDetails")
    public String automaticDetailsToView(Model model, ExaminationDTO examinationDTO){
		
		model.addAttribute("examinationId", examinationDTO.getExaminationId());
		
		return "examination/examinationDetails";
    }		
	
    /**
	 * 考试信息dataGrid 
	 */
	@ResponseBody
	@RequestMapping(value = "examDataGrid")
    public Map<String, Object> examDataGrid(ExamDTO examDTO, Model model){
		
		int pageNo = Integer.parseInt(request.getParameter("page"));
	    int pageSize = Integer.parseInt(request.getParameter("rows"));
	    
		Map<String, Object> resultMap = examService.findExamByPage(pageNo, pageSize, examDTO);
		if(resultMap == null){
			return null;
		}
		
		return resultMap;
    }
	

	/**
	 * 清除自动排考信息
	 */
	@ResponseBody
	@RequestMapping(value = "delAutomatic")
    public Map<String, Object> delAutomatic(Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			reduction.revert();
			map.put("msg", "清空成功");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "清空失败");
			return map;
		}
    }

	/**
	 * 清除自动排考信息
	 */
	@ResponseBody
	@RequestMapping(value = "delexam")
    public Map<String, Object> delexam(Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			reduction.revertExam();
			map.put("msg", "清空成功");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "清空失败");
			return map;
		}
    }
	
	/**
	 * 手动排考  保存记录
	 */
	@ResponseBody
	@RequestMapping(value = "manualInfo")
    public Map<String, Object> manualInfo(ExaminationDTO examinationDTO, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			examinationService.manual(examinationDTO);			
			map.put("msg", "安排成功");
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "安排失败");
			return map;
		}
    }
}
