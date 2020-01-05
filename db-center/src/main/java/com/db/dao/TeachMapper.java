package com.db.dao;

import com.db.entity.Teach;

public interface TeachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teach record);

    int insertSelective(Teach record);

    Teach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teach record);

    int updateByPrimaryKey(Teach record);
}