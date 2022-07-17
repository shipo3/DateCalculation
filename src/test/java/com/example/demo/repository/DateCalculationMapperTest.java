package com.example.demo.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.example.demo.domain.FormulaData;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DateCalculationMapperTest {

    @Autowired
    private DateCalculationMapper datecalculationmapper;

    @Test
    @DataSet(value = "formula.yml")
    public void データー全件取得できること() throws Exception {
	List<FormulaData> actual = datecalculationmapper.findAll();
	assertThat(actual).hasSize(6).contains(new FormulaData(1, "年のみ", "最大値", 100, 0, 0),
		new FormulaData(2, "月と日", "最小値", 0, -100, -1000), new FormulaData(3, "年を超える月", "プラス", -1, 13, 0),
		new FormulaData(4, "月を超える日", "マイナス", 0, 1, -31), new FormulaData(5, "閏年", "基準日が閏年", 0, 0, 1),
		new FormulaData(6, "閏年", "計算結果が閏年", 2, 0, 1));
    }

    @Test
    @DataSet(value = "formula.yml")
    public void 新規登録ができること() throws Exception {
	FormulaData fd = new FormulaData(7, "年を超える日", "プラス", 1, 0, -366);
	datecalculationmapper.insertOne(fd);
	List<FormulaData> actual = datecalculationmapper.findAll();
	assertThat(actual).hasSize(7);
    }

    @Test
    @DataSet(value = "formula.yml")
    public void データ1件削除できること() throws Exception {
	FormulaData fd = new FormulaData(7, "年を超える日", "プラス", 1, 0, -366);
	datecalculationmapper.deleteOne(fd);
	List<FormulaData> actual = datecalculationmapper.findAll();
	assertThat(actual).hasSize(6);
    }

    // Optionalのメソッドを使う形
    @Test
    @DataSet(value = "formula.yml")
    public void キーに紐づく1件の更新が出来ること_Optionalメソッド() {
	datecalculationmapper.updateOne(3, "年を超える月", "マイナス", 1, -13, 0);
	FormulaData actual = datecalculationmapper.findOne(3).orElseThrow();
	assertThat(actual).isEqualTo(new FormulaData(3, "年を超える月", "マイナス", 1, -13, 0));
    }

    // Optionalのメソッドを使わない形
    @Test
    @DataSet(value = "formula.yml")
    public void キーに紐づく1件の更新が出来ること() throws Exception {
	datecalculationmapper.updateOne(4, "月を超える日", "プラス", 0, -1, 31);
	Optional<FormulaData> actual = datecalculationmapper.findOne(4);
	FormulaData fd = actual.get();

	assertEquals(4, fd.getId());
	assertEquals("月を超える日", fd.getName());
	assertEquals("プラス", fd.getDetail());
	assertEquals(0, fd.getYear());
	assertEquals(-1, fd.getMonth());
	assertEquals(31, fd.getDay());
    }

    // hasValueメソッドを使う形
    @Test
    @DataSet(value = "formula.yml")
    public void キーに紐づく1件の更新が出来ること_hasValue() throws Exception {
	datecalculationmapper.updateOne(4, "月を超える日", "マイナス", 0, 1, -31);
	Optional<FormulaData> actual = datecalculationmapper.findOne(4);
	assertThat(actual).hasValue(new FormulaData(4, "月を超える日", "マイナス", 0, 1, -31));
    }

    // Optionalのメソッドを使う形
    @Test
    @DataSet(value = "formula.yml")
    public void 検索_1件して結果がキーに紐づく1件だけ取得出来ること_Optionalメソッド() {
	FormulaData actual = datecalculationmapper.findOne(1).orElseThrow();
	assertThat(actual).isEqualTo(new FormulaData(1, "年のみ", "最大値", 100, 0, 0));
    }

    // Optionalのメソッドを使わない形
    // assertEquals を使って
    @Test
    @DataSet(value = "formula.yml")
    public void 検索_1件して結果がキーに紐づく1件だけ取得出来ること() throws Exception {
	Optional<FormulaData> actual = datecalculationmapper.findOne(1);
	FormulaData fd = actual.get();

	assertEquals(1, fd.getId());
	assertEquals("年のみ", fd.getName());
	assertEquals("最大値", fd.getDetail());
	assertEquals(100, fd.getYear());
	assertEquals(0, fd.getMonth());
	assertEquals(0, fd.getDay());
    }

    // 存在しないIDにて、取得に失敗した場合
    @Test
    @DataSet(value = "formula.yml")
    public void 存在しないIDに紐づく一件を検索するとOptinalEmptyが返ること() {
	Optional<FormulaData> actual = datecalculationmapper.findOne(8);
//	assertThat(actual).isEqualTo(Optional.empty());
	assertThat(actual).isEmpty();
    }

    // 存在しないIDにて、取得に失敗した場合
    // Optionalのメソッドを使わない形
    @Test
    @DataSet(value = "formula.yml")
    public void 存在しないIDに紐づく一件を検索するとExceptionが発生すること() throws Exception {
	Optional<FormulaData> actual = datecalculationmapper.findOne(8);
	assertThatThrownBy(() -> actual.get()).isInstanceOf(RuntimeException.class);
    }

}