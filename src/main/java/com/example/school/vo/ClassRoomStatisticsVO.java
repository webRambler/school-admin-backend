package com.example.school.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 班级成绩统计信息
 */
@Data
public class ClassRoomStatisticsVO {

    private Long classId;
    private String className;
    private String grade;
    private String major;
    private Long studentCount;
    private BigDecimal avgScore;
    private BigDecimal maxScore;
    private BigDecimal minScore;
    private Long passCount;
    private Double passRate;
}
