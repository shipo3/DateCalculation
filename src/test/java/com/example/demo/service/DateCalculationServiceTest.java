package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import com.example.demo.repository.DateCalculationMapper;

class DateCalculationServiceTest {

    // サンプル これは成功する！
//    @Test
//    void myFirstTest() {
//	assertEquals(2, 1 + 1);
//	assertEquals(2, 1 + 3, "失敗です。");
//	assertTrue(2 < 1, () -> "失敗メッセージ");
//    }
    private DateCalculationService dateCalculationService;

    // コンストラクタインジェクションの場合の書き方
    @Mock
    private DateCalculationMapper dateCalculationMapper;

    @BeforeEach
    public void setUp() {
	dateCalculationService = new DateCalculationService(dateCalculationMapper);
    }

    // 日付計算処理のテスト
    @ParameterizedTest
    @ValueSource(strings = { "2022/05/01", "2024/02/29", "2022/02/28" })
    void testDateAdjust(String inputDate) {
	List<String> expected = Arrays.asList("2022/05/01", "2024/03/01", "2024/02/29");
	assertIterableEquals(expected, dateCalculationService.dateAdjust(inputDate));
    }
}