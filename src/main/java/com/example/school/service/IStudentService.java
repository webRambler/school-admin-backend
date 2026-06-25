package com.example.school.service;

import com.example.school.entity.Student;
import com.example.school.vo.StudentWithClassVO;
import com.example.school.vo.StudentCourseScoreVO;

import java.util.List;

public interface IStudentService {

    Student createStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    List<Student> getStudentsByName(String name);

    List<Student> getStudentsByClassId(Long classId);

    List<Student> getStudentsByGender(String gender);

    Student updateStudent(Long id, Student studentDetails);

    void deleteStudent(Long id);

    // ====== 连接查询 ======

    StudentWithClassVO getStudentWithClass(Long studentId);

    List<StudentWithClassVO> getStudentsWithClassByClassId(Long classId);

    List<StudentWithClassVO> getAllStudentsWithClass();

    List<StudentCourseScoreVO> getStudentCourseScores(Long studentId);

    Long countCoursesByStudentId(Long studentId);
}
