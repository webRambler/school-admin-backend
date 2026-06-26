package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ClassRoomUpdateRequest {

    private Long collegeId;

    @Size(max = 50, message = "班级名称不能超过50个字符")
    private String name;

    @Size(max = 20, message = "年级不能超过20个字符")
    private String grade;

    @Size(max = 50, message = "专业不能超过50个字符")
    private String major;

    private Long homeroomTeacherId;
}
