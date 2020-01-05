package com.db.dao;

import com.db.entity.Examination;

public interface ExaminationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Examination record);

    int insertSelective(Examination record);

    Examination selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Examination record);

    int updateByPrimaryKey(Examination record);
}