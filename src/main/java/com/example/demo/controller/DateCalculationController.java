package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
	if (CollectionUtils.isEmpty(formulaDataList)) {
	    String error = "登録されている計算式はありません。";
	    model.addAttribute("complete", error);
	}
	model.addAttribute("fdList", formulaDataList);
	return "calculation/top";
    }

    @PostMapping("/top")
    public String calculationResult(@RequestParam("inputDate") String inputDate, Model model) {
	// 入力日が空の場合はエラーを出す
	if (inputDate.isEmpty()) {
	    String error = "＊基準日を入力して下さい。";
	    model.addAttribute("inputError", error);
	    inputDate = "                    ";
	    model.addAttribute("id", inputDate);
	    // 計算式の取得表示
	    List<FormulaData> formulaDataList = dateCalculationService.getAll();
	    model.addAttribute("fdList", formulaDataList);

	    return "calculation/top";
	}
	// inputにて取得した日付をLocalDate型にする
	// 入力日がある場合は計算処理を行い結果出力する
	// LocalDateリストから取り出してyyyy/MM/ddのフォーマットにする
	List<String> resultList = dateCalculationService.calculate(LocalDate.parse(inputDate))
			.stream()
			.map(result -> result.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
			.collect(Collectors.toList());

	model.addAttribute("resultList", resultList);

	// 入力された基準日の表示
	String inputDate2 = inputDate.replace("-", "/");
	model.addAttribute("id", inputDate2);
	// 計算式の取得表示
	List<FormulaData> formulaDataList = dateCalculationService.getAll();
	model.addAttribute("fdList", formulaDataList);

	return "calculation/top";
    }

    // 「新規登録」押下後の画面取得
    @GetMapping("/new")
    public String form(@ModelAttribute FormulaData formulaData, Model model) {
	model.addAttribute("formuladata", new FormulaData());
	return "calculation/new";
    }

    // 新規登録にて「次へ」押下時 バリデーションチェック行なう
    @PostMapping("/new-confirm")
    public String confirm(@Validated FormulaData formulaData, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "calculation/new";
	}
	// 入力エラーなければ確認画面へ進む
	return "calculation/new-confirm";
    }

    // パス指定で確認画面にアクセス時TOPに戻す
    @GetMapping("/new-confirm")
    public String backtop() {
	return "redirect:/calculation/top";
    }

    // 確認画面にて「登録する」押下時 エラーがなければDBに新規登録してtop画面に戻る
    @PostMapping("/complete")
    public String create(@Validated FormulaData formulaData, BindingResult result, Model model,
		    RedirectAttributes redirectAttributes) {

	if (result.hasErrors()) {
	    redirectAttributes.addFlashAttribute("complete", "新規登録失敗しました。");
	    return "calculation/new";
	}
	dateCalculationService.insertOne(formulaData);
	redirectAttributes.addFlashAttribute("complete", "新規登録完了しました。");
	return "redirect:/calculation/top";
    }

    // top.htmlにて[更新]押下時にchange.htmlを表示
    @GetMapping("/change/id={id}")
    public String change(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
//	System.out.println(dateCalculationService.getOne(id));

	Optional<FormulaData> fd = dateCalculationService.getOne(id);
	if (fd.isEmpty()) {
	    System.out.println("値がnullです");
	    redirectAttributes.addFlashAttribute("complete", "存在しないIDです。");
	    return "redirect:/calculation/top";
	}
	FormulaData fd2 = fd.get();
	model.addAttribute("formulaData", fd2);
	return "calculation/change";
    }

    // change.htmlにて[次へ]押下時 バリデーションチェック行なう
    @PostMapping("/change-confirm")
    public String changeConfirm(@Validated FormulaData formulaData, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "calculation/change";
	}
	// 入力エラーなければ確認画面へ進む
	return "calculation/change-confirm";
    }

    // 確認画面から戻った時
    @PostMapping("/change/id={id}")
    public String changeBack(FormulaData formulaData, Model model) {
	return "calculation/change";
    }

    // パス指定で確認画面にアクセス時TOPに戻す
    @GetMapping("/change-confirm")
    public String backTop() {
	return "redirect:/calculation/top";
    }

    // 確認画面にて「更新する」押下時 エラーがなければDBに更新登録してchnge.htmlに戻る
    @PostMapping("/change-complete")
    public String update(@Validated FormulaData formulaData, BindingResult result, Model model,
		    RedirectAttributes redirectAttributes) {

	if (result.hasErrors()) {
	    redirectAttributes.addFlashAttribute("complete", "更新失敗しました。");
	    return "calculation/change";
	}

	dateCalculationService.updateOne(
			formulaData.getId(),
			formulaData.getName(),
			formulaData.getDetail(),
			formulaData.getYear(),
			formulaData.getMonth(),
			formulaData.getDay());
	redirectAttributes.addFlashAttribute("complete", "更新完了しました。");
	return "redirect:/calculation/top";
    }

    // calculation.htmlにて[削除]押下時、DBから削除する
    @PostMapping("delete/id={id}")
    public String delete(@PathVariable int id, @ModelAttribute FormulaData formulaData,
		    RedirectAttributes redirectAttributes) {
	dateCalculationService.deleteOne(formulaData);
	redirectAttributes.addFlashAttribute("complete", "削除完了しました。");
	return "redirect:/calculation/top";
    }
}
