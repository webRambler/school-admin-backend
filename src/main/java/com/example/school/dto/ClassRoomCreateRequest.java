package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ClassRoomCreateRequest {

    @NotNull(message = "学院ID不能为空")
    private Long collegeId;

    @NotBlank(message = "班级名称不能为空")
    @Size(max = 50, message = "班级名称不能超过50个字符")
    private String name;

    @NotBlank(message = "年级不能为空")
    @Size(max = 20, message = "年级不能超过20个字符")
    private String grade;

    @NotBlank(message = "专业不能为空")
    @Size(max = 50, message = "专业不能超过50个字符")
    private String major;

    @Size(max = 20, message = "班主任姓名不能超过20个字符")
    private String teacher;
}
