package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.FormulaData;
import com.example.demo.repository.DateCalculationMapper;

@Service
public class DateCalculationService {
    private final DateCalculationMapper dateCalculationMapper;

    public DateCalculationService(DateCalculationMapper dateCalculationMapper) {
	this.dateCalculationMapper = dateCalculationMapper;
    }

//DB処理
//select1件
    public FormulaData getOne(int id) {
	return dateCalculationMapper.findOne(id);
    }

//select全件
    public List<FormulaData> getAll() {
	return dateCalculationMapper.findAll();
    }

    // insert
    public void insertOne(FormulaData fd) {
	dateCalculationMapper.insertOne(fd);
    }

    // update
    public void updateOne(int id, String name, String detail, int year, int month, int day) {
	dateCalculationMapper.updateOne(id, name, detail, year, month, day);
    }

    // delete
    public void deleteOne(FormulaData fd) {
	dateCalculationMapper.deleteOne(fd);
    }

    // 日付の加減計算処理
    public List<LocalDate> dateAdjust(String inputDate) {
	// inputにて取得した日付をLocalDate型にする
	LocalDate ld = LocalDate.parse(inputDate);
	// 結果を入れるリストを作る
//	List<String> resultList = new ArrayList<String>();
	List<LocalDate> resultList = new ArrayList<LocalDate>();

	// DBの計算式が入ったデータを取得する
	List<FormulaData> formulaDatas = dateCalculationMapper.findAll();
	// 取得したデータから取り出して日付をプラスする
	resultList = formulaDatas
		.forEach(fd -> ld.plusYears(fd.getYear()).plusMonths(fd.getMonth()).plusDays(fd.getDay()));

//	for (FormulaData fd : formulaDatas) {
//	    LocalDate result1 = ld.plusYears(fd.getYear()).plusMonths(fd.getMonth()).plusDays(fd.getDay());	 
	// 計算結果をStringに戻す
//	    String result2 = result1.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	// 結果リストに格納する
//	    resultList.add(result2);
//	}
	return resultList;
    }
}
