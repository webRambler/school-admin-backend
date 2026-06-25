package com.example.school.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClassRoom {

    private Long id;
    private Long collegeId;
    private String name;
    private String grade;
    private String major;
    private String teacher;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
