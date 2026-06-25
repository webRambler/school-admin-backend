package com.example.school.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 课程成绩统计信息
 */
@Data
public class CourseStatisticsVO {

    private Long courseId;
    private String courseName;
    private BigDecimal credit;
    private Long studentCount;
    private BigDecimal avgScore;
    private BigDecimal maxScore;
    private BigDecimal minScore;
    private Long passCount;
    private Double passRate;
}
