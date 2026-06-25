package com.example.school.mapper;

import com.example.school.entity.Student;
import com.example.school.vo.StudentWithClassVO;
import com.example.school.vo.StudentCourseScoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    // 新增学生
    int insertStudent(Student student);

    // 查询所有学生
    List<Student> selectAllStudents();

    // 根据ID查询学生
    Student selectStudentById(Long id);

    // 根据姓名模糊查询学生
    List<Student> selectStudentsByName(String name);

    // 根据班级ID查询学生
    List<Student> selectStudentsByClassId(Long classId);

    // 根据性别查询学生
    List<Student> selectStudentsByGender(String gender);

    // 更新学生
    int updateStudent(Student student);

    // 删除学生
    int deleteStudent(Long id);

    // ====== 连接查询 ======

    // 查询学生及其班级信息（LEFT JOIN class_room）
    StudentWithClassVO selectStudentWithClass(Long studentId);

    // 查询班级下所有学生及其班级信息
    List<StudentWithClassVO> selectStudentsWithClassByClassId(Long classId);

    // 查询所有学生及其班级信息
    List<StudentWithClassVO> selectAllStudentsWithClass();

    // 查询学生的所有课程和成绩（三表连接：student + score + course）
    List<StudentCourseScoreVO> selectStudentCourseScores(Long studentId);

    // 统计学生选修课程数量
    Long countCoursesByStudentId(Long studentId);
}
