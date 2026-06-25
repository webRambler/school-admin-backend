package com.example.school.repository.impl;

import com.example.school.entity.Student;
import com.example.school.mapper.StudentMapper;
import com.example.school.repository.IStudentRepository;
import com.example.school.vo.StudentCourseScoreVO;
import com.example.school.vo.StudentWithClassVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepositoryImpl implements IStudentRepository {

    private final StudentMapper studentMapper;

    public StudentRepositoryImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public int insertStudent(Student student) {
        return studentMapper.insertStudent(student);
    }

    @Override
    public List<Student> selectAllStudents() {
        return studentMapper.selectAllStudents();
    }

    @Override
    public Student selectStudentById(Long id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    public List<Student> selectStudentsByName(String name) {
        return studentMapper.selectStudentsByName(name);
    }

    @Override
    public List<Student> selectStudentsByClassId(Long classId) {
        return studentMapper.selectStudentsByClassId(classId);
    }

    @Override
    public List<Student> selectStudentsByGender(String gender) {
        return studentMapper.selectStudentsByGender(gender);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public int deleteStudent(Long id) {
        return studentMapper.deleteStudent(id);
    }

    // ====== 连接查询 ======

    @Override
    public StudentWithClassVO selectStudentWithClass(Long studentId) {
        return studentMapper.selectStudentWithClass(studentId);
    }

    @Override
    public List<StudentWithClassVO> selectStudentsWithClassByClassId(Long classId) {
        return studentMapper.selectStudentsWithClassByClassId(classId);
    }

    @Override
    public List<StudentWithClassVO> selectAllStudentsWithClass() {
        return studentMapper.selectAllStudentsWithClass();
    }

    @Override
    public List<StudentCourseScoreVO> selectStudentCourseScores(Long studentId) {
        return studentMapper.selectStudentCourseScores(studentId);
    }

    @Override
    public Long countCoursesByStudentId(Long studentId) {
        return studentMapper.countCoursesByStudentId(studentId);
    }

    @Override
    public List<StudentWithClassVO> searchStudentsWithClass(String gender, String className, String name, String major, Integer age) {
        return studentMapper.searchStudentsWithClass(gender, className, name, major, age);
    }
}
