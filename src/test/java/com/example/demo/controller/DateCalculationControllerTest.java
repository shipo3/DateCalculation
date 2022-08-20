package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.domain.FormulaData;
import com.example.demo.service.DateCalculationService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class DateCalculationControllerTest {
    // @Mockでモックにしたインスタンスの注入先となるインスタンス
    @InjectMocks
    private DateCalculationController dateCalculationController;
    // モック（スタブ）に置き換えたいインスタンス
    @Mock
    private DateCalculationService dateCalculationService;
    // HTTPリクエストを投げるためにMockMvc使う
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
	this.mockMvc = MockMvcBuilders.standaloneSetup(dateCalculationController)
			.build();
    }

    @Test
    void topページをGETすると結果が200となり計算式が表示されたtopページが返ること() throws Exception {
	List<FormulaData> fd = Arrays.asList(
			new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
			new FormulaData(2, "月と日", "最小値", 0, -100, -1000),
			new FormulaData(3, "年を超える月", "プラス", -1, 13, 0));
	doReturn(fd).when(dateCalculationService)
			.getAll();
	mockMvc.perform(get("/calculation/top"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("fdList", fd))
			.andExpect(view().name("calculation/top"));

	verify(dateCalculationService).getAll();
    }

    @Test
    void 入力日が空の場合はエラーが表示されたtopページが返ること() throws Exception {
	List<FormulaData> fd = Arrays.asList(
			new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
			new FormulaData(2, "月と日", "最小値", 0, -100, -1000),
			new FormulaData(3, "年を超える月", "プラス", -1, 13, 0));
	doReturn(fd).when(dateCalculationService)
			.getAll();
	mockMvc.perform(post("/calculation/top").param("inputDate", ""))
			.andExpect(model().attribute("inputError", "＊基準日を入力して下さい。"))
			.andExpect(model().attribute("id", "                    "))
			.andExpect(model().attribute("fdList", fd))
			.andExpect(view().name("calculation/top"));

	verify(dateCalculationService).getAll();
    }

    @Test
    void 入力日が空でない場合は計算結果が表示されたtopページが返ること() throws Exception {
	LocalDate date1 = LocalDate.of(2122, 05, 01);
	LocalDate date2 = LocalDate.of(2011, 04, 07);
	List<LocalDate> resultList = List.of(date1, date2);
	doReturn(resultList).when(dateCalculationService)
			.calculate(LocalDate.parse("2022-05-01"));
	List<String> resultListStr = resultList.stream()
			.map(result -> result.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
			.collect(Collectors.toList());
	mockMvc.perform(post("/calculation/top").param("inputDate", "2022-05-01"))
			.andExpect(model().attribute("resultList", resultListStr))
			.andExpect(model().attribute("id", "2022/05/01"))
			.andExpect(view().name("calculation/top"));

	verify(dateCalculationService).calculate(LocalDate.parse("2022-05-01"));
    }

}
