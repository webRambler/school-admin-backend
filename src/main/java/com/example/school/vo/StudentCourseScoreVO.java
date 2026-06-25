package com.example.school.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学生课程成绩信息（学生 + 课程 + 成绩，用于查询学生所有选修课程及成绩）
 */
@Data
public class StudentCourseScoreVO {

    // 成绩信息
    private Long scoreId;
    private BigDecimal score;
    private String examType;
    private LocalDateTime scoreCreateTime;

    // 课程信息
    private Long courseId;
    private String courseName;
    private BigDecimal credit;
    private Integer hours;
    private String semester;
}
