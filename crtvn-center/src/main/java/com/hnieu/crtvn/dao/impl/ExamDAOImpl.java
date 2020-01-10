package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IExamDAO;
import com.hnieu.crtvn.entity.Exam;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class ExamDAOImpl extends BaseDao<Exam> implements IExamDAO {

	@Override
	public void saveExam(Exam exam) {
		save(exam);
	}

	@Override
	public void flushExam() {
		flush();
	}

	@Override
	public void clearExam() {
		clear();
	}

	@Override
	public void commitExam() {
		commit();
	}

	@Override
	public void startTExam() {
		startT();
	}

	@Override
	public List<Exam> findExamByEId(Map<String, Object> params) {

		return find(getHql(params), params);
	}

	@Override
	public List<Exam> findExamByPage(int pageNo, int pageSize, Map<String, Object> params) {

		return findByPage(getHql(params), pageNo, pageSize, params);
	}
	
	@Override
	public long findExamCountByGrid(Map<String, Object> params) {

		return find(getHql(params),params).size(); 
	}
	
	private String getHql(Map<String, Object> params){
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select e from  Examination ex, Exam e  ");		
		hql.append(" where  e.examination.id = ex.id  ");
					
		hql.append(" and  ex.id=:examinationId  ");

		return hql.toString();
	}

	@Override
	public void truncateExam() {

		truncate("Exam");
	}

	@Override
	public void deleteExam(Exam exam) {
		
		delete(exam);
	}

	
}
