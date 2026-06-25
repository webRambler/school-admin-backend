package com.example.school.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Score {

    private Long id;
    private Long studentId;
    private Long courseId;
    private BigDecimal score;
    private String examType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
