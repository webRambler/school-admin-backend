package com.example.school.repository.impl;

import com.example.school.entity.Teacher;
import com.example.school.mapper.TeacherMapper;
import com.example.school.repository.ITeacherRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherRepositoryImpl implements ITeacherRepository {

    private final TeacherMapper teacherMapper;

    public TeacherRepositoryImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public int insertTeacher(Teacher teacher) {
        return teacherMapper.insertTeacher(teacher);
    }

    @Override
    public List<Teacher> selectAllTeachers() {
        return teacherMapper.selectAllTeachers();
    }

    @Override
    public Teacher selectTeacherById(Long id) {
        return teacherMapper.selectTeacherById(id);
    }

    @Override
    public List<Teacher> selectTeachersByName(String name) {
        return teacherMapper.selectTeachersByName(name);
    }

    @Override
    public List<Teacher> selectTeachersByTitle(String title) {
        return teacherMapper.selectTeachersByTitle(title);
    }

    @Override
    public List<Teacher> searchTeachers(String name, Long collegeId) {
        return teacherMapper.searchTeachers(name, collegeId);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public int deleteTeacher(Long id) {
        return teacherMapper.deleteTeacher(id);
    }
}
