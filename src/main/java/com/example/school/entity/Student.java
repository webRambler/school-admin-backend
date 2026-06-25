package com.example.school.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Student {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private Long classId;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
