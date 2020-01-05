package com.hnieu.crtvn.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface IEtimeService {
	
	void insertEtime(Timestamp stime, Timestamp etime);
	
	void deleteEtime(int id);
	
	Map<String, Object> findEtimeByPage(int pageNo, int pageSize);

	List<Map<String,Object>> findEtime(int mark);
}
