package com.hnieu.crtvn.controller.college;

import com.hnieu.crtvn.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "college")
@SuppressWarnings("unchecked")
public class CollegeController {
	
	@Autowired
	private ICollegeService collegeService;
	
	/**
	 *院系信息下拉框
	 */

	//@PreAuthorize("hasAuthority('admin')")
	@ResponseBody
	@RequestMapping(value = "collegeSelect")
    public List<Map<String, Object>> collegeSelect(Model model){

		return collegeService.findCollege();
    }
}
