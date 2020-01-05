package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Examination;

import java.util.List;
import java.util.Map;

public interface IExaminationDAO {
	
	Examination findExaminationById(int id);
	
	void saveExamination(Examination examination);
	
	//非分页
	List<Examination> findExaminationByMark(Map<String, Object> params);

	//分页
	List<Examination> findExaminationByPage(int pageNo, int pageSize, Map<String, Object> params);

	long findExaminationCountByGrid(Map<String, Object> params);

	void updateExamination(Examination examination);

	void deleteExamination(Examination examination);

	List<Examination> findExaminationByCourseId(Map<String, Object> params);

	//下载数据
	List<Examination> findExamination(Map<String, Object> params);
}
