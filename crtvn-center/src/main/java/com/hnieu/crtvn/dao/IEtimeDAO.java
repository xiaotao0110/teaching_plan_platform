package com.hnieu.crtvn.dao;

import java.util.List;
import java.util.Map;

import com.hnieu.crtvn.entity.Etime;

public interface IEtimeDAO {
	
	void insertEtime(Etime etime);
	
	void deleteEtime(Etime etime);
	
	void updateEtime(Etime etime);
	
	Etime findEtimeById(int id);
	
	List<Etime> findEtimeByPage(int pageNo, int pageSize);

	long findEtimeCountByGrid();

	List<Etime> findEtime(Map<String, Object> params);

	List<Etime>  findEtimeByMark(int mark);
	
}
