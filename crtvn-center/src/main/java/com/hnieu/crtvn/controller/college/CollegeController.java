package com.hnieu.crtvn.controller.college;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hnieu.crtvn.service.ICollegeService;

@Controller
@RequestMapping(value = "college")
public class CollegeController {
	
	@Autowired
	private ICollegeService collegeService;
	
	/**
	 *院系信息下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "collegeSelect")
    public List<Map<String, Object>> collegeSelect(Model model){

		return collegeService.findCollege();
    }
}
