package com.example.school.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Course {

    private Long id;
    private Long teacherId;
    private String name;
    private BigDecimal credit;
    private Integer hours;
    private String semester;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
