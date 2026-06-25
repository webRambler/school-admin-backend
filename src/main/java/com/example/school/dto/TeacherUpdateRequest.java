package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class TeacherUpdateRequest {

    private Long collegeId;

    @Size(max = 20, message = "姓名不能超过20个字符")
    private String name;

    @Pattern(regexp = "^(男|女)$", message = "性别只能为男或女")
    private String gender;

    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 100, message = "年龄不能超过100")
    private Integer age;

    @Size(max = 30, message = "职称不能超过30个字符")
    private String title;

    @Size(max = 15, message = "手机号不能超过15个字符")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;
}
