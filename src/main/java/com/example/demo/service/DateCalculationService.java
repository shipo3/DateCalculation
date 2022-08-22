package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.FormulaData;
import com.example.demo.repository.DateCalculationMapper;

@Service
public class DateCalculationService {
    private final DateCalculationMapper dateCalculationMapper;

    public DateCalculationService(DateCalculationMapper dateCalculationMapper) {
	this.dateCalculationMapper = dateCalculationMapper;
    }

    // DB処理
    // select1件
    public Optional<FormulaData> getOne(int id) {
	return dateCalculationMapper.findOne(id);
    }

    // select全件
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

    /**
     * Adds or subtracts dates based on formulas registered in the database to or
     * from the retrieved date
     *
     * @param inputDate the date to be calculated, not {@code null}
     */
    public List<LocalDate> calculate(LocalDate iD) {
	// 結果を入れるリストを作る
	List<LocalDate> resultList = new ArrayList<LocalDate>();

	// DBの計算式が入ったデータを取得する
	List<FormulaData> formulaDatas = dateCalculationMapper.findAll();
	// 取得したデータから取り出して日付をプラスする
	// Collection の中身を利用して、何かの値を作成し、リストに変換
	resultList = formulaDatas.stream()
			.map(
					fd -> iD.plusYears(fd.getYear())
							.plusMonths(fd.getMonth())
							.plusDays(fd.getDay()))
			.collect(Collectors.toList());

	return resultList;
    }
}
