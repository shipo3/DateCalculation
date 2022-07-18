package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.FormulaData;

@Mapper
public interface DateCalculationMapper {
    List<FormulaData> findAll();

    Optional<FormulaData> findOne(int id);

    void insertOne(FormulaData fd);

    void updateOne(@Param("id") int id, @Param("name") String name, @Param("detail") String detail,
	    @Param("year") int year, @Param("month") int month, @Param("day") int day);

    void deleteOne(FormulaData fd);

}
