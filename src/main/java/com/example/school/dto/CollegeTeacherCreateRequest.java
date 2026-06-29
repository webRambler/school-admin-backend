package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CollegeTeacherCreateRequest {

    @NotNull(message = "学院ID不能为空")
    private Long collegeId;

    @NotNull(message = "教师ID不能为空")
    private Long teacherId;

    /** 是否为院长(0:否 1:是)，默认0 */
    private Integer isDean = 0;
}
