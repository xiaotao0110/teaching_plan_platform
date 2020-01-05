package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Academician;

public interface AcademicianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Academician record);

    int insertSelective(Academician record);

    Academician selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Academician record);

    int updateByPrimaryKey(Academician record);
}