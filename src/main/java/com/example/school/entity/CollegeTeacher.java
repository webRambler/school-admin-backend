package com.example.school.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CollegeTeacher {

    private Long id;
    private Long collegeId;
    private Long teacherId;
    private Integer isDean;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
