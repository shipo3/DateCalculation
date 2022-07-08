package com.example.demo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

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
		new FormulaData(2, "月と日", "最小値", 0, -100, -1000), new FormulaData(3, "年を超える日", "プラス", 1, -13, 0),
		new FormulaData(4, "月を超える日", "マイナス", 0, -1, 31), new FormulaData(5, "閏年", "基準日が閏年", 0, 0, 1),
		new FormulaData(6, "閏年", "計算結果が閏年", 2, 0, 1));
    }

//    @Test
//    @DataSet(value = "formula.yml")
//    public void 新規登録ができること() throws Exception {
//	FormulaData actual = 7, "年を超える日", "プラス", 1, 0, -366;
//	FormulaData actual = datecalculationmapper.insertOne(7, "年を超える日", "プラス", 1, 0, -366);
//	assertThat(actual).hasSize(7);
//    }

//    @Test
//    void testUpdateOne() {
//	fail("まだ実装されていません");
//    }

//    @Test
//    void testDeleteOne() {
//	fail("まだ実装されていません");
//    }
}
