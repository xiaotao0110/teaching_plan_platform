package com.hnieu.crtvn.dao;

import com.hnieu.crtvn.entity.Examination;

public interface ExaminationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Examination record);

    int insertSelective(Examination record);

    Examination selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Examination record);

    int updateByPrimaryKey(Examination record);
}