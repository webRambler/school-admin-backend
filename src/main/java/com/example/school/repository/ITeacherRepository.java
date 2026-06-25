package com.example.school.repository;

import com.example.school.entity.Teacher;

import java.util.List;

public interface ITeacherRepository {

    int insertTeacher(Teacher teacher);

    List<Teacher> selectAllTeachers();

    Teacher selectTeacherById(Long id);

    List<Teacher> selectTeachersByName(String name);

    List<Teacher> selectTeachersByTitle(String title);

    int updateTeacher(Teacher teacher);

    int deleteTeacher(Long id);
}
