package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @Test
    void 新規登録ページをGETすると結果が200となり新規登録ページが返ること() throws Exception {
	mockMvc.perform(get("/calculation/new"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("formuladata", new FormulaData()))
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録ページで登録名がNULLの状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/new-confirm").param("id", "1")
					.param("detail", "最大値")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録ページで登録名が空白の状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/new-confirm").param("id", "1")
					.param("name", " ")
					.param("detail", "最大値")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録ページで登録名が31文字以上の状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/new-confirm").param("id", "1")
					.param("name", "あかさたなはまやらわあかさたなはまやらわあかさたなはまやらわあ")
					.param("detail", "最大値")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録ページで説明がNULLの状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/new-confirm").param("id", "1")
					.param("name", "年のみ")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録ページで説明が空白の状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/new-confirm").param("id", "1")
					.param("name", "年のみ")
					.param("detail", " ")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録ページで説明が51文字以上の状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/new-confirm").param("id", "1")
					.param("name", "年のみ")
					.param("detail", "あかさたなはまやらわあかさたなはまやらわあかさたなはまやらわあかさたなはまやらわあかさたなはまやらわあ")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/new"));
    }

    @Test
    void 新規登録確認ページで登録処理に成功するとサービスで処理されてTOPページに遷移すること() throws Exception {
	mockMvc.perform(
			post("/calculation/complete").param("id", "1")
					.param("name", "年のみ")
					.param("detail", "最大値")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("redirect:/calculation/top"));

	verify(dateCalculationService).insertOne(any());
    }

    @Test
    void 更新ページをGETすると結果が200となり更新ページが返ること() throws Exception {
	Optional<FormulaData> fd = Optional.of(new FormulaData(1, "年のみ", "最大値", 100, 0, 0));
	doReturn(fd).when(dateCalculationService)
			.getOne(1);
	mockMvc.perform(get("/calculation/change/id={id}", 1))
			.andExpect(status().isOk())
			.andExpect(model().attribute("formulaData", fd.get()))
			.andExpect(view().name("calculation/change"));

	verify(dateCalculationService).getOne(1);
    }

    @Test
    void 存在しないIDにて更新ページをGETするとTOPページに遷移すること() throws Exception {
	doReturn(Optional.empty()).when(dateCalculationService)
			.getOne(999);
	mockMvc.perform(get("/calculation/change/id={id}", "999"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/calculation/top"));

	verify(dateCalculationService).getOne(999);
    }

    // 新規登録画面同様のバリデーションチェックが機能するか1パターン確認
    @Test
    void 更新ページで登録名がNULLの状態で次へ進むと例外情報が入った状態で画面が返ること() throws Exception {
	mockMvc.perform(
			post("/calculation/change-confirm").param("id", "1")
					.param("detail", "最大値")
					.param("year", "100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("calculation/change"));
    }

    @Test
    void 更新確認ページで更新処理に成功するとサービスで処理されてTOPページに遷移すること() throws Exception {
	mockMvc.perform(
			post("/calculation/change-complete").param("id", "1")
					.param("name", "年のみ")
					.param("detail", "最小値")
					.param("year", "-100")
					.param("month", "0")
					.param("day", "0"))
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("redirect:/calculation/top"));

	verify(dateCalculationService).updateOne(1, "年のみ", "最小値", -100, 0, 0);
    }

    @Test
    void TOPページで削除するとサービスで処理されてTOPページに遷移すること() throws Exception {
	mockMvc.perform(post("/calculation/delete/id={id}", 1))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/calculation/top"));

	verify(dateCalculationService).deleteOne(any());
    }

}
