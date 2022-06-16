package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.service.DateCalculationService;

public class DateCalculationControllerTest {
    // コンストラクタインジェクション＋Mockitoの書き方
    @InjectMocks
    private DateCalculationController dateCalculationController;

    @Mock
    private DateCalculationService dateCalculationService;
    // HTTPリクエストを投げるためにMockMvc使う
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
	this.mockMvc = MockMvcBuilders.standaloneSetup(dateCalculationController).build();
    }

    @Test
    @DisplayName("計算式リストが空の場合エラーメッセージが表示されること")
    void testGetFormulaData() throws Exception {
	mockMvc.perform(post("top")).andExpect(model().hasErrors()).andExpect(view().name("calculation/top"));
//		.andExpect(model().attribute("complete", error))
    }

//    @Test
//    void testCalculationResult() {
//	fail("まだ実装されていません");
//    }

    @Test
    @DisplayName("新規登録画面に遷移すること")
    void testForm() throws Exception {
	mockMvc.perform(get("/new")).andExpect(status().isOk()).andExpect(view().name("calculation/new"));
    }

    @Test
    void testConfirm() {
	fail("まだ実装されていません");
    }

    @Test
    @DisplayName("パス指定で確認画面にアクセス時TOPに戻ること")
    void testBacktop() throws Exception {
	mockMvc.perform(get("/new-confirm")).andExpect(status().isOk())
		.andExpect(view().name("redirect:/calculation/top"));
    }

//    @Test
//    public void 新規登録ページで登録名がNULLの状態で登録処理を行うと例外情報が入った状態で画面が返る事() throws Exception {
//	sut.perform(post("/register").param("dateId", "TEST")).andExpect(status().isOk())
//		.andExpect(view().name("register"));
//    }
//
//    @Test
//    public void 新規登録ページで説明が空の状態で登録処理を行うと例外情報が入った状態で画面が返る事() throws Exception {
//	sut.perform(post("/register").param("dateId", "TEST").param("dateName", "")).andExpect(status().isOk())
//		.andExpect(view().name("register"));
//    }

    @Test
    void testChange() {
	fail("まだ実装されていません");
    }

    @Test
    void testChangeConfirm() throws Exception {
	mockMvc.perform(post("top")).andExpect(model().hasErrors()).andExpect(view().name("calculation/top"));
//	.andExpect(model().attribute("complete", error))
    }

    @Test
    @DisplayName("確認画面からchange画面に戻ること")
    void testChangeBack() throws Exception {
	mockMvc.perform(post("/change/id={id}")).andExpect(status().isOk())
		.andExpect(view().name("calculation/change"));
    }

    @Test
    @DisplayName("パス指定で確認画面にアクセス時TOPに戻ること")
    void testChangeBacktop() throws Exception {
	mockMvc.perform(get("/change-confirm")).andExpect(status().isOk())
		.andExpect(view().name("redirect:/calculation/top"));
    }

    @Test
    void testUpdate() {
	fail("まだ実装されていません");
    }

    @Test
    void testDelete() {
	fail("まだ実装されていません");
    }

}
