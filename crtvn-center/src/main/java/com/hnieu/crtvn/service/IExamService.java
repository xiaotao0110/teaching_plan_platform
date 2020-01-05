package com.hnieu.crtvn.service;

import com.hnieu.crtvn.dto.ExamDTO;
import com.hnieu.crtvn.entity.Exam;
import com.hnieu.crtvn.vo.ExamVO;

import java.util.List;
import java.util.Map;

public interface IExamService {
	
	Map<String,String> exam(ExamDTO examDTO) throws Exception;
	
	Map<String, Object> findExamByPage(int pageNo, int pageSize, ExamDTO examDTO);
	
	List<ExamVO> getExamVOList(List<Exam> examList);
		
}
