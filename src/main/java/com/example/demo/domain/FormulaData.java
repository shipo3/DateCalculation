package com.example.demo.domain;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
//新規登録、更新時の入力チェック
public class FormulaData {
    @Size(min = 1, max = 5, message = "ID番号を入力して下さい。")
    private int id;

    @Size(min = 1, max = 30, message = "登録名を入力して下さい。")
    private String name;

    @Size(max = 100)
    private String detail;
    private int year;
    private int month;
    private int day;
}
