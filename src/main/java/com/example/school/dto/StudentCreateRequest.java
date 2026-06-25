package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class StudentCreateRequest {

    @NotBlank(message = "姓名不能为空")
    @Size(max = 20, message = "姓名不能超过20个字符")
    private String name;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^(男|女)$", message = "性别只能为男或女")
    private String gender;

    @NotNull(message = "年龄不能为空")
    @Min(value = 1, message = "年龄不能小于1")
    @Max(value = 150, message = "年龄不能超过150")
    private Integer age;

    @NotNull(message = "班级ID不能为空")
    private Long classId;

    @Size(max = 15, message = "手机号不能超过15个字符")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;
}
