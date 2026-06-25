package com.example.school.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Teacher {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String title;
    private String department;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
