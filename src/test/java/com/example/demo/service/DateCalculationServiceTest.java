package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.FormulaData;
import com.example.demo.repository.DateCalculationMapper;

@ExtendWith(MockitoExtension.class)
class DateCalculationServiceTest {

    // コンストラクタインジェクション＋Mockitoの書き方
    @InjectMocks
    private DateCalculationService dateCalculationService;

    @Mock
    private DateCalculationMapper dateCalculationMapper;

    @Test
    void Mapperから取得したデーターをそのまま返すこと() {
	List<FormulaData> fd = Arrays.asList(new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
		new FormulaData(2, "月と日", "最小値", 0, -100, -1000), new FormulaData(3, "年を超える月", "プラス", -1, 13, 0));
	doReturn(fd).when(dateCalculationMapper).findAll();
	List<FormulaData> actual = dateCalculationService.getAll();
	assertThat(actual).hasSize(3).isEqualTo(fd);

	verify(dateCalculationMapper).findAll();
    }

    @Test
    void 全件取得時レコードが０件だった場合NULLとなること() {
	List<FormulaData> fd = Arrays.asList();
	doReturn(fd).when(dateCalculationMapper).findAll();
	List<FormulaData> actual = dateCalculationService.getAll();
	assertThat(actual).isEmpty();

	verify(dateCalculationMapper).findAll();
    }

    @Test
    void データーを新規登録できること() {
	FormulaData newFd = new FormulaData(3, "年を超える月", "プラス", -1, 13, 0);
	doReturn(List.of(newFd)).when(dateCalculationMapper).findAll();
	dateCalculationService.insertOne(newFd);
	List<FormulaData> actual = dateCalculationService.getAll();
	assertThat(actual).hasSize(1);

	verify(dateCalculationMapper).insertOne(newFd);
    }

    @Test
    void 指定したデーターを1件削除できること() {
	List<FormulaData> fd = Arrays.asList(new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
		new FormulaData(2, "月と日", "最小値", 0, -100, -1000));
	doReturn(fd).when(dateCalculationMapper).findAll();
	FormulaData del = new FormulaData(3, "年を超える月", "プラス", -1, 13, 0);
	dateCalculationService.deleteOne(del);
	List<FormulaData> actual = dateCalculationService.getAll();
	assertThat(actual).hasSize(2).contains(new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
		new FormulaData(2, "月と日", "最小値", 0, -100, -1000));

	verify(dateCalculationMapper).deleteOne(del);
    }

    // hasValueにて検証する形
    @Test
    void 指定したIDのデーターを更新できること_hasValue() throws Exception {
	FormulaData fd = new FormulaData(1, "年のみ", "最小値", -100, 0, 0);
	int id = 1;
	doReturn(Optional.of(fd)).when(dateCalculationMapper).findOne(id);
	dateCalculationService.updateOne(1, "年のみ", "最小値", -100, 0, 0);
	Optional<FormulaData> actual = dateCalculationService.getOne(id);
	assertThat(actual).hasValue(new FormulaData(1, "年のみ", "最小値", -100, 0, 0));

	verify(dateCalculationMapper).updateOne(1, "年のみ", "最小値", -100, 0, 0);
    }

    // Optionalのメソッドを使う形
    @Test
    void 検索_1件して結果が指定したIDに紐づく1件だけ取得出来ること() {
	FormulaData fd = new FormulaData(2, "月と日", "最小値", 0, -100, -1000);
	int id = 2;
	doReturn(Optional.of(fd)).when(dateCalculationMapper).findOne(id);
	Optional<FormulaData> actual = dateCalculationService.getOne(id);
	assertThat(actual).isEqualTo(Optional.of(fd));

	verify(dateCalculationMapper).findOne(id);
    }

    // 存在しないIDにて、取得に失敗した場合
    @Test
    public void 存在しないIDに紐づく一件を検索するとOptionalEmptyが返ること() {
	int id = 4;
	doReturn(Optional.empty()).when(dateCalculationMapper).findOne(id);
	Optional<FormulaData> actual = dateCalculationService.getOne(id);
	assertThat(actual).isEqualTo(Optional.empty());

	verify(dateCalculationMapper).findOne(id);
    }

    // 日付計算処理のテスト
//    @ParameterizedTest
//    @ValueSource(strings = { "2022/05/01", "2024/02/29", "2022/02/28" })
//    void testDateAdjust(String inputDate) {
//	List<String> expected = Arrays.asList("2022/05/01", "2024/03/01", "2024/02/29");
//	assertIterableEquals(expected, dateCalculationService.dateAdjust(inputDate));
//    }
}