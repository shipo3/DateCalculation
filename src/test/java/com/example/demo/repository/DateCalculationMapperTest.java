package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.domain.FormulaData;

@MybatisTest
@TestPropertySource(locations = "classpath:test.properties")
public class DateCalculationMapperTest {

    @Autowired
    private DateCalculationMapper mapperTest;

    @Test
    public void 検索_全件して結果をリストで取得出来る事() throws Exception {
	List<FormulaData> actual = mapperTest.findAll();
	assertEquals(3, actual.size());
    }
//    @Test
//    void testFindOne() {
//	fail("まだ実装されていません");
//    }
//
//    @Test
//    void testInsertOne() {
//	fail("まだ実装されていません");
//    }
//
//    @Test
//    void testUpdateOne() {
//	fail("まだ実装されていません");
//    }
//
//    @Test
//    void testDeleteOne() {
//	fail("まだ実装されていません");
//    }
}
