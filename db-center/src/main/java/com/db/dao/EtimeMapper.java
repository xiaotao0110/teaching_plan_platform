package com.db.dao;

import com.db.entity.Etime;

public interface EtimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Etime record);

    int insertSelective(Etime record);

    Etime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Etime record);

    int updateByPrimaryKey(Etime record);
}