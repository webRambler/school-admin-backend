package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class StudentUpdateRequest {

    @Size(max = 20, message = "姓名不能超过20个字符")
    private String name;

    @Pattern(regexp = "^(男|女)$", message = "性别只能为男或女")
    private String gender;

    @Min(value = 1, message = "年龄不能小于1")
    @Max(value = 150, message = "年龄不能超过150")
    private Integer age;

    private Long classId;

    @Size(max = 15, message = "手机号不能超过15个字符")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;
}
