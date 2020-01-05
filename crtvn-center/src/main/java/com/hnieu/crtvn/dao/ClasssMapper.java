package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Classs;

public interface ClasssMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classs record);

    int insertSelective(Classs record);

    Classs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classs record);

    int updateByPrimaryKey(Classs record);
}