package com.hnieu.crtvn.service;

import com.hnieu.crtvn.dto.ExaminationDTO;
import com.hnieu.crtvn.vo.DownloadExamVO;
import com.hnieu.crtvn.vo.DownloadExaminationVO;

import java.util.List;
import java.util.Map;

public interface IExaminationService {
	
	Map<String,String> automatic(ExaminationDTO examinationDTO)throws Exception;
	
	Map<String, Object> findExaminationByPage(int pageNo, int pageSize, ExaminationDTO examinationDTO);
	
	//监考老师下拉框  查询条件 
	List<Map<String,Object>> findExamTeacherSelect(ExaminationDTO examinationDTO);

	void manual(ExaminationDTO examinationDTO);
	
	List<DownloadExaminationVO> exportExcelExamination(ExaminationDTO examinationDTO);
	
	List<DownloadExamVO> exportExcelExam(ExaminationDTO examinationDTO);
}
