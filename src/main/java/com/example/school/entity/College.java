package com.example.school.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class College {

    private Long id;
    private String name;
    private String code;
    private String description;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
