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
	assertThat(actual).hasSize(6);
	 	.contains( 
	 			new FormulaData(1, "年のみ","最大値",100,0,0)
//	 			new FormulaData(id: 2, name: "月と日",detail: "最小値",year:0,month: -100,day: -1000), 
//	 			new FormulaData(id: 3, name: "年を超える日",detail: "プラス",year: 1,month: -13,day: 0), 
//	 			new FormulaData(id: 4, name:"月を超える日",detail: "マイナス",year: 0, month: -1,day: 31),
//	 			new FormulaData(id: 5, name: "閏年", detail: "基準日が閏年", year: 0,month: 0,day: 1), 
//	 			new FormulaData(id: 6, name: "閏年", detail: "計算結果が閏年", year: 2, month: 0, day: 1)
	 		);

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
