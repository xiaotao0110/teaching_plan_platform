package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Teach;

public interface TeachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teach record);

    int insertSelective(Teach record);

    Teach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teach record);

    int updateByPrimaryKey(Teach record);
}