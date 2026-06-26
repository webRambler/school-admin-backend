package com.example.school.service;

import com.example.school.entity.Teacher;

import java.util.List;

public interface ITeacherService {

    // 创建教师
    Teacher createTeacher(Teacher teacher);

    // 查询所有教师（分页）
    List<Teacher> getAllTeachers();

    // 根据ID查询教师
    Teacher getTeacherById(Long id);

    // 根据姓名模糊查询教师
    List<Teacher> getTeachersByName(String name);

    // 根据职称查询教师
    List<Teacher> getTeachersByTitle(String title);

    // 多条件查询教师（姓名模糊 + 学院ID可选）
    List<Teacher> searchTeachers(String name, Long collegeId);

    // 更新教师信息
    Teacher updateTeacher(Long id, Teacher teacherDetails);

    // 删除教师
    void deleteTeacher(Long id);
}
