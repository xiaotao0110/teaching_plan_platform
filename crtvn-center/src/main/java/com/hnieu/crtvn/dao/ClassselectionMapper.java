package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Classselection;

public interface ClassselectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classselection record);

    int insertSelective(Classselection record);

    Classselection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classselection record);

    int updateByPrimaryKey(Classselection record);
}