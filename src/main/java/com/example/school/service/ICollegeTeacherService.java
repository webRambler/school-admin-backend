package com.example.school.service;

import com.example.school.dto.CollegeTeacherCreateRequest;
import com.example.school.entity.CollegeTeacher;
import com.example.school.vo.CollegeTeacherVO;

import java.util.List;

public interface ICollegeTeacherService {

    /** 创建学院-教师关系（含重复校验、院长唯一性校验） */
    CollegeTeacher createRelation(CollegeTeacherCreateRequest request);

    /** 根据ID查询关系记录 */
    CollegeTeacher getById(Long id);

    /** 查询某学院下所有教师（含教师姓名、职称、是否院长） */
    List<CollegeTeacherVO> getTeachersByCollege(Long collegeId);

    /** 查询某教师关联的所有学院 */
    List<CollegeTeacherVO> getCollegesByTeacher(Long teacherId);

    /** 设置/取消院长（同一学院至多一位院长） */
    void setIsDean(Long id, Integer isDean);

    /** 删除关系记录 */
    void deleteRelation(Long id);

    /** 统计学院下关联的教师数量 */
    Long countTeachersByCollege(Long collegeId);
}
