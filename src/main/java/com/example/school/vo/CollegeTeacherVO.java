package com.example.school.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学院-教师关系视图对象（包含学院和教师基本信息）
 */
@Data
public class CollegeTeacherVO {

    private Long id;
    private Long collegeId;
    private String collegeName;
    private Long teacherId;
    private String teacherName;
    private String teacherTitle;
    private Integer isDean;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
