package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calculation")
public class DateCalculationController {
    @GetMapping("/top")
    public String getTop() {
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
	String inputDate2 = inputDate.replace("-", "/");
	model.addAttribute("id", inputDate2);
	return "calculation/top";
    }
}

//public class MyCollectionController {
//    private final MyCollectionService myCollectionService;
//
//    public MyCollectionController(MyCollectionService myCollectionService) {
//	this.myCollectionService = myCollectionService;
//    }

//    @GetMapping("/top")
//    public String top(Model model) {
//	List<Kimono> mcList = myCollectionService.getMyCollections();
//	System.out.println(mcList);
//	model.addAttribute("ml", mcList);
//	return "calculation/top";
//    }
