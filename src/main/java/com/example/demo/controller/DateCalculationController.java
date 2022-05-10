package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.FormulaData;
import com.example.demo.service.DateCalculationService;

@Controller
@RequestMapping("/calculation")
public class DateCalculationController {
    private final DateCalculationService dateCalculationService;

    public DateCalculationController(DateCalculationService dateCalculationService) {
	this.dateCalculationService = dateCalculationService;
    }

    @GetMapping("/top")
    public String getFormulaData(Model model) {
	// DBに入っている既存の計算式を表示させる
	List<FormulaData> formulaDataList = dateCalculationService.getAll();
	System.out.println(formulaDataList);
	model.addAttribute("fdList", formulaDataList);
	return "calculation/top";
    }

    @PostMapping("/top")
    public String postTop(@RequestParam("inputDate") String inputDate, Model model) {
	if (inputDate.isEmpty()) {
	    String error = "＊基準日を入力して下さい。";
	    model.addAttribute("inputError", error);
	    inputDate = "　";
	    model.addAttribute("id", inputDate);
	    return "calculation/top";
	}
	List<String> re = dateCalculationService.dateAdjust(inputDate);
	model.addAttribute("resultList", re);
	String inputDate2 = inputDate.replace("-", "/");
	model.addAttribute("id", inputDate2);

	List<FormulaData> formulaDataList = dateCalculationService.getAll();
	System.out.println(formulaDataList);
	model.addAttribute("fdList", formulaDataList);

//	if (CollectionUtils.isEmpty(formulaDataList)) {
//	}
//	for (String result : re) {
//	    model.addAttribute("result", result);
//	}
	return "calculation/top";
    }

}
