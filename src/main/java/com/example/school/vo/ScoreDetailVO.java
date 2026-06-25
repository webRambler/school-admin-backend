package com.example.school.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩详情（成绩 + 学生 + 课程信息，三表连接查询）
 */
@Data
public class ScoreDetailVO {

    // 成绩信息
    private Long scoreId;
    private BigDecimal score;
    private String examType;
    private LocalDateTime scoreCreateTime;

    // 学生信息
    private Long studentId;
    private String studentName;
    private String gender;
    private String phone;

    // 课程信息
    private Long courseId;
    private String courseName;
    private BigDecimal credit;
    private Integer hours;
}
