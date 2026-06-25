package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class CourseUpdateRequest {

    private Long teacherId;

    @Size(max = 50, message = "课程名称不能超过50个字符")
    private String name;

    @DecimalMin(value = "0.5", message = "学分不能小于0.5")
    private BigDecimal credit;

    private Integer hours;

    @Size(max = 20, message = "学期不能超过20个字符")
    private String semester;
}
