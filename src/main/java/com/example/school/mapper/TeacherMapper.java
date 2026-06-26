package com.example.school.mapper;

import com.example.school.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper {

    // 新增教师
    int insertTeacher(Teacher teacher);

    // 查询所有教师
    List<Teacher> selectAllTeachers();

    // 根据ID查询教师
    Teacher selectTeacherById(Long id);

    // 根据姓名模糊查询教师
    List<Teacher> selectTeachersByName(String name);

    // 根据职称查询教师
    List<Teacher> selectTeachersByTitle(String title);

    // 多条件查询教师（姓名模糊 + 学院ID可选）
    List<Teacher> searchTeachers(@Param("name") String name, @Param("collegeId") Long collegeId);

    // 更新教师
    int updateTeacher(Teacher teacher);

    // 删除教师
    int deleteTeacher(Long id);
}
