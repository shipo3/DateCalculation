package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.FormulaData;

@Mapper
public interface DateCalculationMapper {
    List<FormulaData> findAll();

    public FormulaData findOne(int id);

    public void insertOne(FormulaData fd);

    public void updateOne(@Param("id") int id, @Param("name") String name, @Param("detail") String detail,
	    @Param("year") int year, @Param("month") int month, @Param("day") int day);

    public void deleteOne(FormulaData fd);

}
