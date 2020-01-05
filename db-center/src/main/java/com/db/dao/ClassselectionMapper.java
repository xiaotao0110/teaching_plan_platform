package com.db.dao;

import com.db.entity.Classselection;

public interface ClassselectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classselection record);

    int insertSelective(Classselection record);

    Classselection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classselection record);

    int updateByPrimaryKey(Classselection record);
}