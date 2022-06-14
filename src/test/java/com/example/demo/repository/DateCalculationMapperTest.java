package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.domain.FormulaData;

@MybatisTest
@TestPropertySource(locations = "classpath:application-database-h2.yml")
public class DateCalculationMapperTest {

    @Autowired
    private DateCalculationMapper mapperTest;

    @Test
    public void 新規登録が出来る事() throws Exception {
	FormulaData formula = createFormula(14, "年のみ", "最大値", 100, 0, 0);

	mapperTest.insertOne(formula);
	Optional<FormulaData> actual = mapperTest.findOne(4);
	FormulaData fd = actual.get();

	assertEquals(4, fd.getId());
    }

    @Test
    public void 検索_全件して結果をリストで取得出来る事() throws Exception {
	List<FormulaData> actual = mapperTest.findAll();
	assertEquals(3, actual.size());
    }

    @Test
    public void キーに紐づく1件の更新が出来る事() throws Exception {
	mapperTest.updateOne(14, "年と月", "最小値", -100, -100, 0);
	Optional<FormulaData> actual = mapperTest.findOne(4);
	FormulaData fd = actual.get();

	assertEquals(4, fd.getId());
	assertEquals("年と月", fd.getName());
	assertEquals("最小値", fd.getDetail());
	assertEquals(-100, fd.getYear());
	assertEquals(-100, fd.getMonth());
	assertEquals(0, fd.getDay());
    }

    @Test
    public void キーに紐づく1件の削除が出来る事() throws Exception {
	FormulaData formula = createFormula(14, "年のみ", "最大値", 100, 0, 0);
	mapperTest.deleteOne(formula);
	List<FormulaData> actual = mapperTest.findAll();

	// 追加したid=4が削除され、データーが3件に戻っていることの確認
	assertEquals(3, actual.size());
    }

    @Test
    public void 検索_1件して結果がキーに紐づく1件だけ取得出来る事() throws Exception {
	Optional<FormulaData> actual = mapperTest.findOne(1);
	FormulaData fd = actual.get();

	assertEquals(1, fd.getId());
	assertEquals("すべて未設定", fd.getName());
	assertEquals("初期値", fd.getDetail());
	assertEquals(0, fd.getYear());
	assertEquals(0, fd.getMonth());
	assertEquals(0, fd.getDay());
    }

    private FormulaData createFormula(int ID, String 登録名, String 説明, int 加減年, int 加減月, int 加減日) {
	FormulaData formula = new FormulaData();
	formula.setId(ID);
	formula.setName(登録名);
	formula.setName(説明);
	formula.setYear(加減年);
	formula.setMonth(加減月);
	formula.setDay(加減日);
	return formula;
    }

}
