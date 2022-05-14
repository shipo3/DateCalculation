package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	// 入力日が空の場合はエラーを出す
	if (inputDate.isEmpty()) {
	    String error = "＊基準日を入力して下さい。";
	    model.addAttribute("inputError", error);
	    inputDate = "　";
	    model.addAttribute("id", inputDate);
	    return "calculation/top";
	}
	// 入力日がある場合は計算処理を行い結果出力する
	List<String> re = dateCalculationService.dateAdjust(inputDate);
	model.addAttribute("resultList", re);
	String inputDate2 = inputDate.replace("-", "/");
	model.addAttribute("id", inputDate2);

	List<FormulaData> formulaDataList = dateCalculationService.getAll();
	System.out.println(formulaDataList);
	model.addAttribute("fdList", formulaDataList);

	return "calculation/top";
    }

//「新規登録」押下後の画面取得  /　登録完了メッセージ取得
    @GetMapping("/new")
    public String form(FormulaData formulaData, Model model, @ModelAttribute("complete") String complete) {
	model.addAttribute("formuladata", new FormulaData());
	return "calculation/new";
    }

//確認画面から戻った時
    @PostMapping("/new")
    public String formback(FormulaData formulaData, Model model) {
	model.addAttribute("title", "calc_new");
	return "calculation/new";
    }

//新規登録にて「次へ」押下時　バリデーションチェック行なう
    @PostMapping("/new_confirm")
    public String confirm(@Validated FormulaData formulaData, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    model.addAttribute("title", "calc_new");
	    return "calculation/new";
	}
	// 入力エラーなければ確認画面へ進む
	model.addAttribute("title", "calc_confirm");
	return "calculation/new_confirm";

    }

//確認画面にて「登録する」押下時　エラーがなければDBに新規登録してnew画面に戻る
    @PostMapping("/complete")
    public String create(@Validated FormulaData formulaData, BindingResult result, Model model,
	    RedirectAttributes redirectAttributes) {

	if (result.hasErrors()) {
	    model.addAttribute("title", "calc_new");
	    return "calculation/new";
	}
	dateCalculationService.insertOne(formulaData);
	redirectAttributes.addFlashAttribute("complete", "新規登録完了しました。");
	return "redirect:/calculation/new";
    }
    // calculation.htmlにて[更新]押下時にchange.htmlを表示

    @GetMapping("change/id={id}")
    public String change(@PathVariable("id") int id, Model model) {
	model.addAttribute("formulaData", dateCalculationService.getOne(id));
	return "calculation/change";
    }

    // change.htmlにて変更を登録する
    @PostMapping("change/id={id}")
    public String update(@ModelAttribute FormulaData fd, Model model) {
	dateCalculationService.updateOne(fd.getId(), fd.getName(), fd.getDetail(), fd.getYear(), fd.getMonth(),
		fd.getDay());
	return "redirect:/top";
    }

    // calculation.htmlにて[削除]押下

    @PostMapping("delete/id={id}")
    public String delete(@PathVariable int id, @ModelAttribute FormulaData fd) {
	dateCalculationService.deleteOne(fd);
	return "redirect:/top";
    }

}