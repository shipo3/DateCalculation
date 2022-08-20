package com.example.demo.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//新規登録、更新時の入力チェック
public class FormulaData {
    private int id;
    @NotBlank(message = "30文字以内で登録名を入力して下さい。")
    @Size(max = 30, message = "30文字以内で登録名を入力して下さい。")
    private String name;

    @NotBlank(message = "50文字以内で説明を入力して下さい。")
    @Size(max = 50, message = "50文字以内で説明を入力して下さい。")
    private String detail;
    private int year;
    private int month;
    private int day;
}
