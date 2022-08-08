package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.FormulaData;
import com.example.demo.repository.DateCalculationMapper;

@ExtendWith(MockitoExtension.class)
class DateCalculationServiceTest {

    // @Mockでモックにしたインスタンスの注入先となるインスタンス
    @InjectMocks
    private DateCalculationService dateCalculationService;

    // モック（スタブ）に置き換えたいインスタンス
    @Mock
    private DateCalculationMapper dateCalculationMapper;

    @Test
    void Mapperから取得したデーターをそのまま返すこと() {
	List<FormulaData> fd = Arrays.asList(
		    new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
		    new FormulaData(2, "月と日", "最小値", 0, -100, -1000),
		    new FormulaData(3, "年を超える月", "プラス", -1, 13, 0));
	doReturn(fd).when(dateCalculationMapper).findAll();
	List<FormulaData> actual = dateCalculationService.getAll();
	assertThat(actual).hasSize(3).isEqualTo(fd);

	verify(dateCalculationMapper).findAll();
    }

    @Test
    void 全件取得時レコードが０件だった場合空のリストとなること() {
	List<FormulaData> fd = Collections.emptyList();
	doReturn(fd).when(dateCalculationMapper).findAll();
	List<FormulaData> actual = dateCalculationService.getAll();
	assertThat(actual).isEmpty();

	verify(dateCalculationMapper).findAll();
    }

    @Test
    void データーを新規登録できること() {
	FormulaData newFd = new FormulaData(3, "年を超える月", "プラス", -1, 13, 0);
	dateCalculationService.insertOne(newFd);

	verify(dateCalculationMapper).insertOne(newFd);
    }

    @Test
    void 指定したデーターを1件削除できること() {
	FormulaData del = new FormulaData(3, "年を超える月", "プラス", -1, 13, 0);
	dateCalculationService.deleteOne(del);

	verify(dateCalculationMapper).deleteOne(del);
    }

    @Test
    void 指定したIDのデーターを更新できること() {
	dateCalculationService.updateOne(1, "年のみ", "最小値", -100, 0, 0);

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
    void 存在しないIDに紐づく一件を検索するとOptionalEmptyが返ること() {
	int id = 4;
	doReturn(Optional.empty()).when(dateCalculationMapper).findOne(id);
	Optional<FormulaData> actual = dateCalculationService.getOne(id);
	assertThat(actual).isEqualTo(Optional.empty());

	verify(dateCalculationMapper).findOne(id);
    }

    // 日付計算処理のテスト
    @Test
    public void 計算基準日にNULLを渡すとNullPointerExceptionとなる事() throws Exception {
	assertThatThrownBy(() -> {
	    dateCalculationService.dateAdjust(null);
	}).isInstanceOf(NullPointerException.class);
    }

    // 日付計算処理のテスト
    @Test
    void 日付加減処理が正しく行なわれるかを検証する() {
	String inputDate = "2022-05-01";
	List<FormulaData> formulaDatas = Arrays.asList(
		    new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
		    new FormulaData(2, "月と日", "最小値", 0, -100, -1000));
	doReturn(formulaDatas).when(dateCalculationMapper).findAll();

	List<String> excected = new ArrayList<String>() {
	    {
		add("2122/05/01");
		add("2011/04/07");
	    }
	};

	List<String> actual = new ArrayList<String>();
	actual = dateCalculationService.dateAdjust(inputDate).stream()
		    .map(result -> result.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
		    .collect(Collectors.toList());
	assertThat(actual).isEqualTo(excected);

	verify(dateCalculationMapper).findAll();
    }
}
