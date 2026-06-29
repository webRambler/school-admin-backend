package com.example.school.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学院详情（学院信息 + 院长信息）
 */
@Data
public class CollegeWithDeanVO {

    // 学院信息
    private Long id;
    private String name;
    private String code;
    private String description;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 院长信息
    private Long deanId;
    private String deanName;
    private String deanPhone;
}
