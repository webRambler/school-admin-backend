package com.example.school.repository;

import com.example.school.entity.Student;
import com.example.school.vo.StudentCourseScoreVO;
import com.example.school.vo.StudentWithClassVO;

import java.util.List;

public interface IStudentRepository {

    int insertStudent(Student student);

    List<Student> selectAllStudents();

    Student selectStudentById(Long id);

    List<Student> selectStudentsByName(String name);

    List<Student> selectStudentsByClassId(Long classId);

    List<Student> selectStudentsByGender(String gender);

    int updateStudent(Student student);

    int deleteStudent(Long id);

    // ====== 连接查询 ======

    StudentWithClassVO selectStudentWithClass(Long studentId);

    List<StudentWithClassVO> selectStudentsWithClassByClassId(Long classId);

    List<StudentWithClassVO> selectAllStudentsWithClass();

    List<StudentCourseScoreVO> selectStudentCourseScores(Long studentId);

    Long countCoursesByStudentId(Long studentId);

    List<StudentWithClassVO> searchStudentsWithClass(String gender, String className, String name, String major, Integer age);
}
