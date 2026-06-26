package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CollegeUpdateRequest {

    @Size(max = 50, message = "学院名称不能超过50个字符")
    private String name;

    @Size(max = 20, message = "学院代码不能超过20个字符")
    private String code;

    @Size(max = 200, message = "学院简介不能超过200个字符")
    private String description;

    @Size(max = 20, message = "联系电话不能超过20个字符")
    private String phone;
}
