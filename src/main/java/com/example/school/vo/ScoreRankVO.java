package com.example.school.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 成绩排名信息
 */
@Data
public class ScoreRankVO {

    private Integer rank;
    private Long studentId;
    private String studentName;
    private String className;
    private BigDecimal score;
}
