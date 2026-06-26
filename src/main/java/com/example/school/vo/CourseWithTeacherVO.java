package com.example.school.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程详情（课程信息 + 授课教师姓名）
 */
@Data
public class CourseWithTeacherVO {

    private Long id;
    private Long teacherId;
    private String name;
    private BigDecimal credit;
    private Integer hours;
    private String semester;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 授课教师姓名
    private String teacherName;
}
