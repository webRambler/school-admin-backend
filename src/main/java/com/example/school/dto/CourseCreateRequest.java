package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class CourseCreateRequest {

    @NotBlank(message = "课程名称不能为空")
    @Size(max = 50, message = "课程名称不能超过50个字符")
    private String name;

    @NotNull(message = "学分不能为空")
    @DecimalMin(value = "0.5", message = "学分不能小于0.5")
    private BigDecimal credit;

    @NotNull(message = "学时不能为空")
    private Integer hours;

    @Size(max = 20, message = "学期不能超过20个字符")
    private String semester;
}
