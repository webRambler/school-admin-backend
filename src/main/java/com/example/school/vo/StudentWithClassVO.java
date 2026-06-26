package com.example.school.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生 + 班级信息（一对一连接查询）
 */
@Data
public class StudentWithClassVO {

    // 学生信息
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private Long classId;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 班级信息
    private String className;
    private String grade;
    private String major;
    private Long homeroomTeacherId;
}
