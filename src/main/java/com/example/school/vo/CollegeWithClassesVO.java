package com.example.school.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 学院详情（学院 + 下属班级列表，用于查询学院及其所有班级）
 */
@Data
public class CollegeWithClassesVO {

    // 学院信息
    private Long collegeId;
    private String collegeName;
    private String collegeCode;
    private String description;
    private String dean;
    private String phone;
    private LocalDateTime createTime;

    // 统计信息
    private Long classCount;
    private Long studentCount;
}
