package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Studentselection;

public interface StudentselectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Studentselection record);

    int insertSelective(Studentselection record);

    Studentselection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Studentselection record);

    int updateByPrimaryKey(Studentselection record);
}