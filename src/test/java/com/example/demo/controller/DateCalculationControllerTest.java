package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.FormulaData;

public class DateCalculationControllerTest {

//    // mockMvcを使う
//    private MockMvc mockMvc;
//    @InjectMocks
//    private DateCalculationController dateCalculationController;
//
//    // コンストラクタインジェクションの場合の書き方
//    @Mock
//    private DateCalculationService dateCalculationService;
//
//    @BeforeEach
//    public void setUp() {
//	this.mockMvc = MockMvcBuilders.standaloneSetup(dateCalculationController).build();
//    }

    private class DateCalculationServiceMock {
	FormulaData fd;

	public void insertOne(FormulaData fd) throws Exception {
	    this.fd = fd;
	}
    }

    @Test
    void testGetFormulaData() {
	fail("まだ実装されていません");
    }

    @Test
    void testCalculationResult() {
	fail("まだ実装されていません");
    }

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
    void testChangeConfirm() {
	fail("まだ実装されていません");
    }

    @Test
    void testChangeBack() {
	fail("まだ実装されていません");
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
